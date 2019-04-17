package com.gui.star.common.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("用户对象信息封装")
public class UserForm {
    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "登录名", required = true)
    private String loginname;

    @ApiModelProperty(value = "密码", required = true)
    private String password;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "联系电话")
    private String phone;
}