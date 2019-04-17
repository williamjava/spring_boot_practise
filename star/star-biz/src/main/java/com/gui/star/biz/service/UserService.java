package com.gui.star.biz.service;

import java.util.List;

import com.gui.star.biz.vo.UserVo;
import com.gui.star.common.form.UserForm;

public interface UserService {
	/**
	 * 查询所有用户信息
	 * 
	 * @return 返回用户列表
	 */
	List<UserVo> queryList();
	
	Long save(UserForm form);
}
