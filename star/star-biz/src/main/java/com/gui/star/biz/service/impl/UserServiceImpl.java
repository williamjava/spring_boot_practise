package com.gui.star.biz.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gui.star.biz.service.UserService;
import com.gui.star.biz.vo.UserVo;
import com.gui.star.dal.mapper.UserMapper;
import com.gui.star.dal.model.User;
import com.gui.star.dal.model.UserExample;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;

	/**
	 * 查询用户信息列表
	 */
	@Override
	public List<UserVo> queryAllUsers() {
		List<UserVo> userVoList = new ArrayList<UserVo>();

		UserExample example = new UserExample();
		List<User> userList = userMapper.selectByExample(example);
		for (User user : userList) {
			UserVo userVo = new UserVo();
			BeanUtils.copyProperties(user, userVo);
			userVoList.add(userVo);
		}
		return userVoList;
	}

}
