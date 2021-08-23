package com.lomtom.test.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 导入日志记录
 * @author lomtom
 * @date 2021/8/12 14:21
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_insurance_import_log")
public class ImportLogPO {

    private Long id;

    /**
     * excel地址
     */
    private String url;

    /**
     * 提示信息
     */
    private String msg;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 执行状态
     */
    private Boolean status;

    /**
     * 执行时间
     */
    private String time;

    /**
     * 所花时间
     */
    private Long total;
}
