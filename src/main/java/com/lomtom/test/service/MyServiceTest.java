package com.lomtom.test.service;

import com.lomtom.test.aspect.ImportLogAnnotationTest;
import com.lomtom.test.model.DictionaryPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author lomtom
 * @date 2021/8/16 10:32
 **/

@Service
public class MyServiceTest {

    private final DictionaryServiceImpl dictionaryService;

    @Autowired
    public MyServiceTest(DictionaryServiceImpl dictionaryService) {
        this.dictionaryService = dictionaryService;
    }




    @ImportLogAnnotationTest
    @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRES_NEW)
    public Object testTransactional() throws Exception {
        DictionaryPO byId = dictionaryService.getById(1427214682892009472L);
        for (int i = 0; i < 2; i++) {
            byId.setId(1427214682892009473L);
            dictionaryService.save(byId);
            throw new Exception("我自个抛出的异常！！！！");
        }
        return "chenggong";
    }

}

