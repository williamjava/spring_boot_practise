package com.gui.star.dal.model;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.util.Date;

@Data
@TableName("user")
public class User {
	@TableId(type = IdType.AUTO)
    private Long id;

    private String loginname;

    private String password;

    private String email;

    private String phone;

    private Date createdAt;

    private Date updatedAt;

    private Byte deleted;
}