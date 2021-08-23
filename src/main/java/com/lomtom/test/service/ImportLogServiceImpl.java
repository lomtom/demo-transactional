package com.lomtom.test.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lomtom.test.mapper.ImportLogMapper;
import com.lomtom.test.model.ImportLogPO;
import org.springframework.stereotype.Service;

/**
 * @author lomtom
 */
@Service("ImportLogServiceImpl1")
public class ImportLogServiceImpl extends ServiceImpl<ImportLogMapper, ImportLogPO>{

    public void insert(ImportLogPO importLog) {
        baseMapper.insert(importLog);
    }
}
