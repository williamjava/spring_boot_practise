package com.gui.star.common.form;

import lombok.Data;

@Data
public class UserForm {
    private Long id;

    private String loginname;

    private String password;

    private String email;

    private String phone;
}