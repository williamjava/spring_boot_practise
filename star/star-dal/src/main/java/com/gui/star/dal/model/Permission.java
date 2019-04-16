package com.gui.star.dal.model;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.util.Date;

@TableName("permission")
@Data
public class Permission {
    @TableId(type = IdType.AUTO)
    private Long id;

    private String name;

    private String resValue;

    private Date createdAt;

    private Date updatedAt;

    private Byte deleted;
}