package com.gui.star.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gui.star.biz.service.UserService;
import com.gui.star.biz.vo.UserVo;
import com.gui.star.common.form.UserForm;
import com.gui.star.common.util.Result;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 用户管理控制器
 * 
 * 注解@ApiOperation用来给API增加说明
 * 
 * 注解@ApiImplicitParams和@ApiImplicitParam用来给参数增加说明
 * 
 * @author wuhoujian
 *
 */
@Api(value = "用户管理Api", tags = "用户相关操作")
@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@ApiOperation(value = "查看用户列表", notes = "查看所有用户信息列表", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiImplicitParam(name = "id", value = "用户ID", required = false, dataType = "Long")
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public String list() {
		List<UserVo> userList = userService.queryList();
		if (!CollectionUtils.isEmpty(userList)) {
			for (UserVo user : userList) {
				System.out.println(user.getLoginname() + "===" + user.getPassword());
			}
		}

		return "Hello, SpringBoot! I have " + userList.size() + "个用户！";
	}

	/**
	 * 保存用户信息
	 * 
	 * @param form
	 * @return
	 */
	@ApiOperation(value = "保存用户信息", notes = "保存用户信息", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "loginname", value = "登录名", required = true, dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "password", value = "登录密码", required = true, dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "email", value = "邮箱", required = false, dataType = "string", paramType = "query") })
	@RequestMapping(value = "/save", method = { RequestMethod.GET, RequestMethod.POST })
	public Result<String> save(UserForm form) {
		userService.save(form);

		return Result.generateSuccess("保存成功");
	}
}
