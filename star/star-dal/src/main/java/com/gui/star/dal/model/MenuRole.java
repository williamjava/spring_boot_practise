package com.gui.star.dal.model;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.util.Date;

@TableName("menu_role")
@Data
public class MenuRole {
    @TableId(type = IdType.AUTO)
    private Long id;

    private Long menuId;

    private Long roleId;

    private Date createdAt;

    private Date updatedAt;

    private Byte deleted;
}