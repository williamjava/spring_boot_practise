package com.gui.star.dal.mapper;

import com.gui.star.dal.model.UserRole;
import com.gui.star.dal.model.UserRoleExample;
import java.util.List;

public interface UserRoleMapper {
    int countByExample(UserRoleExample example);

    int deleteByPrimaryKey(Long id);

    int insert(UserRole record);

    int insertSelective(UserRole record);

    List<UserRole> selectByExample(UserRoleExample example);

    UserRole selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserRole record);

    int updateByPrimaryKey(UserRole record);
}