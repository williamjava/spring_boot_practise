package com.gui.star.web.controller;

import com.gui.star.biz.service.UserService;
import com.gui.star.biz.vo.UserVo;
import com.gui.star.common.form.UserForm;
import com.gui.star.common.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 用户管理控制器
 * @author wuhoujian
 */
@Api(value = "用户管理Api", tags = "用户相关操作")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "查看用户列表", notes = "查看所有用户信息列表", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Result<List<UserVo>> list() {
        List<UserVo> userList = userService.queryList();

        return Result.generateSuccess(userList);
    }

    /**
     * 保存用户信息
     *
     * @param form
     * @return
     */
    @ApiOperation(value = "保存用户信息", notes = "保存用户信息", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result<Long> save(@ModelAttribute UserForm form) {
        return Result.generateSuccess(userService.save(form));
    }
}
