package com.lomtom.test.aspect;

import com.lomtom.test.model.ImportLogPO;
import com.lomtom.test.service.ImportLogServiceImpl;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Date;

/**
 * 记录导入的执行日志
 *
 * @author lomtom
 * @date 2021/8/12 10:50
 **/

@Component
@Aspect
public class ImportLogAspectTest {

    private final ImportLogServiceImpl ImportLogServiceImpl1;
    private final TransactionTemplate transactionTemplate;
    private final DataSourceTransactionManager transactionManager;

    @Autowired
    public ImportLogAspectTest( ImportLogServiceImpl ImportLogServiceImpl1, TransactionTemplate transactionTemplate, DataSourceTransactionManager transactionManager) {
        this.ImportLogServiceImpl1 = ImportLogServiceImpl1;
        this.transactionTemplate = transactionTemplate;
        this.transactionManager = transactionManager;
    }


    @Pointcut(value = "@annotation(com.lomtom.test.aspect.ImportLogAnnotationTest)")
    private void pointAround() {
    }

    @Around(value = "pointAround()")
    public Object aroundAdvise(ProceedingJoinPoint joinPoint) throws Throwable {
        ImportLogPO importLog = new ImportLogPO();
        importLog.setId(1427214682892009472L);
        long startTimeMillis = System.currentTimeMillis();
        //调用 proceed() 方法才会真正的执行实际被代理的方法
        Object result;
        try {
            result = joinPoint.proceed();
            importLog.setStatus(true);
            importLog.setMsg("没有出错");
        } catch (Throwable throwable) {
            importLog.setStatus(false);
            importLog.setMsg("出现错误");
            throw throwable;
        } finally {
            long execTimeMillis = System.currentTimeMillis() - startTimeMillis;
            importLog.setCreateTime(new Date());
            importLog.setTime(execTimeMillis + "ms");
            importLog.setUrl("这是测试");
            // 方法一（可解决）在service中加事务
            transactionTemplate.execute(arg0 -> {
                ImportLogServiceImpl1.insert(importLog);
                return Boolean.TRUE;
            });
            // 方法二（可解决）在service中加事务
//            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
//            def.setReadOnly(false);
//            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
//            TransactionStatus status = transactionManager.getTransaction(def);
//            ImportLogServiceImpl1.insert(importLog);
//            transactionManager.commit(status);
            // 方法三（可解决）在service中加事务
//            加注解
//            ImportLogServiceImpl1.insert(importLog);
            // 方法四（未解决）在service中加事务注解、在切面上加事务注解（换隔离级别无效）
            // 方法五（未解决）未在service中加事务注解、在切面上加事务注解（换隔离级别无效）
            // 方法六（未解决）在service中手动事务回滚、在切面上加事务
            // 方法七 换数据源（未解决，网上说换数据也是一种办法）...
        }
        return result;
    }

}
