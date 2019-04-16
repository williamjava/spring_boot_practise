package com.gui.star.biz.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.gui.star.biz.service.UserService;
import com.gui.star.biz.vo.UserVo;
import com.gui.star.common.form.UserForm;
import com.gui.star.common.util.DomainUtil;
import com.gui.star.common.util.ObjectUtil;
import com.gui.star.dal.mapper.UserMapper;
import com.gui.star.dal.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
	/**
	 * 查询用户信息列表
	 */
	@Override
	public List<UserVo> queryList() {
		List<UserVo> userVoList = new ArrayList<UserVo>();

		List<User> userList = this.selectList(null);
		for (User user : userList) {
			UserVo userVo = new UserVo();
			BeanUtils.copyProperties(user, userVo);
			userVoList.add(userVo);
		}
		return userVoList;
	}

	/**
	 * 保存用户信息
	 */
	@Override
	public void save(UserForm form) {
		User user = ObjectUtil.source2Target(form, User.class);
		DomainUtil.setCommonValueForCreate(user);
		this.insert(user);
		
		log.info("用户ID为：{}", user.getId());
	}

}
