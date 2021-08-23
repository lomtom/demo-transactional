
**需求**：在批量插入数据库时，使用aop进行日志记录这一次插入的结果，如果数据插入的时候出现异常，就使用回滚。

**问题**：
最开始想的是使用spring的事务注解`@Transactional`,异常就回滚。
但是当我的批量插入异常回滚后，我的日志也跟着回滚了，但是需求就是失败也需要记录该次插入失败的信息。

使用：
```java
1、批量插入操作
@Service
public class MyServiceTest {

    private final DictionaryService dictionaryService;
    private final IdGen idGen;

    @Autowired
    public MyServiceTest(DictionaryService dictionaryService, IdGen idGen) {
        this.dictionaryService = dictionaryService;
        this.idGen = idGen;
    }

    //自己写的注解
    @ImportLogAnnotationTest
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public BaseServiceResponse<Object> testTransactional() throws Exception {
        DictionaryPO byId = dictionaryService.getById(1427216115901136896L);
        for (int i = 0; i < 2; i++) {
            byId.setId(idGen.nextId());
            dictionaryService.save(byId);
            //模拟出现异常
            throw new Exception("我自个抛出的异常！！！！");
        }
        return BaseServiceResponse.resp(CodeType.SUCCESS);
    }
}

2、aop切面
@Component
@Aspect
public class ImportLogAspectTest {

    private final IdGen idGen;
    private final ImportLogServiceImpl ImportLogServiceImpl1;

    @Autowired
    public ImportLogAspectTest(IdGen idGen, ImportLogServiceImpl ImportLogServiceImpl1) {
        this.idGen = idGen;
        this.ImportLogServiceImpl1 = ImportLogServiceImpl1;
    }


    @Pointcut(value = "@annotation(com.linan.test.ImportLogAnnotationTest)")
    private void pointAround() {
    }

    @Around(value = "pointAround()")
    public Object aroundAdvise(ProceedingJoinPoint joinPoint) throws Throwable {
        ImportLogPO importLog = new ImportLogPO();
        long startTimeMillis = System.currentTimeMillis();
        Object result;
        try {
            result = joinPoint.proceed();
            BaseServiceResponse<Object> serviceResponse = (BaseServiceResponse) result;
            importLog.setStatus(serviceResponse.getErrorCode() == 0);
            importLog.setMsg("没有出错");
        } catch (Throwable throwable) {
            importLog.setStatus(false);
            importLog.setMsg("出现错误");
            throw throwable;
        } finally {
            long execTimeMillis = System.currentTimeMillis() - startTimeMillis;
            importLog.setId(idGen.nextId());
            importLog.setCreateTime(new Date());
            importLog.setTime(execTimeMillis + "ms");
            importLog.setUrl("这是测试");
            ImportLogServiceImpl1.insert(importLog);
        }
        return result;
    }
}
```

尝试解决：

1. 更改`@Transactional`的隔离级别，没有解决
2. 在批量插入插入代码中，使用手动事务回滚，没有解决
```java
    @ImportLogAnnotationTest
    public BaseServiceResponse<Object> testTransactional() throws Exception {
        DictionaryPO byId = dictionaryService.getById(1427216115901136896L);
        try {
            for (int i = 0; i < 2; i++) {
                byId.setId(idGen.nextId());
                dictionaryService.save(byId);
                throw new Exception("我自个抛出的异常！！！！");
            }
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
        }
        return BaseServiceResponse.resp(CodeType.SUCCESS);
    }
```
3. 将`TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();`加在切面的catch中，没有解决
4. 在aop切面上加上事务注解，没有解决

尝试解决结果（无外乎两种结果）：
1. 批量插入回滚了，但是日志也进行回滚了
2. 日志未进行回滚，但是批量插入也成功了一部分（异常抛出前的一部分）

注：使用手动回滚还会显示`No transaction aspect-managed TransactionStatus in scope`


虽然说我已经解决了，我是直接在切面里面调用日志写入的方法和批量插入的方法上加事务注解，但是，这次的体验给我留下了很多疑惑，自己百度也没能解开我心中的疑惑，所以想来问一下小傅哥。

1. 为什么在切面上加上事务注解会没有用，从表面上看，就是切面（`joinPoint.proceed()`）调用的方法。。。
   这里有我自己的猜想？（事务代理的是MyServiceTest，而在事物内，无法调用本类的方法，所以我猜想，我使用aop，代理的也是MyServiceTest，所以才会导致失效）
2. 为什么手动回滚事务会有`No transaction aspect-managed TransactionStatus in scope`的提示，但是网上绝大多数文章都是这样使用的，也并没有出错？