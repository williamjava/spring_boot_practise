package com.gui.star.biz.service;

import java.util.List;

import com.gui.star.biz.vo.UserVo;

public interface UserService {
	/**
	 * 查询所有用户信息
	 * 
	 * @return 返回用户列表
	 */
	List<UserVo> queryAllUsers();
}
