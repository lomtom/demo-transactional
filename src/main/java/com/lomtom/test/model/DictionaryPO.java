package com.lomtom.test.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

/**
 * 字典
 * @author lomtom
 * @date 2021/7/29 14:13
 **/

@Data
@TableName("tb_data_dictionary")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DictionaryPO{
    @TableId
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    /**
     * 类型
     */
    private String type;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 关键字
     */
    @TableField("`key`")
    private String key;

    /**
     * 名字
     */
    @TableField("`name`")
    private String name;

    /**
     * 值
     */
    @TableField("`value`")
    private String value;

    /**
     * 是否删除
     */
    @TableLogic
    private Boolean deleted;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 描述
     */
    @TableField("`desc`")
    private String desc;
}
