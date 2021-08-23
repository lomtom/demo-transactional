package com.lomtom.test.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lomtom.test.model.ImportLogPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author lomtom
 * @date 2021/8/6 16:53
 **/

@Mapper
public interface ImportLogMapper extends BaseMapper<ImportLogPO> {
}
