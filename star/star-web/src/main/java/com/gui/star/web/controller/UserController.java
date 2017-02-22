package com.gui.star.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gui.star.biz.service.UserService;
import com.gui.star.biz.vo.UserVo;

/**
 * 用户管理控制器
 * 
 * @author wuhoujian
 *
 */
@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping("/list")
	public String list() {
		List<UserVo> userList = userService.queryAllUsers();
		if (!CollectionUtils.isEmpty(userList)) {
			for (UserVo user : userList) {
				System.out.println(user.getLoginname() + "===" + user.getPassword());
			}
		}

		return "Hello, SpringBoot! I have " + userList.size() + "个用户！";
	}
}
