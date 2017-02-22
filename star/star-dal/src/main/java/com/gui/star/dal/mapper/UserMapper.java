package com.gui.star.dal.mapper;

import com.gui.star.dal.model.User;
import com.gui.star.dal.model.UserExample;
import java.util.List;

public interface UserMapper {
    int countByExample(UserExample example);

    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    List<User> selectByExample(UserExample example);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}