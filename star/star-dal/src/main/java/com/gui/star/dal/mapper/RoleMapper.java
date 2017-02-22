package com.gui.star.dal.mapper;

import com.gui.star.dal.model.Role;
import com.gui.star.dal.model.RoleExample;
import java.util.List;

public interface RoleMapper {
    int countByExample(RoleExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Role record);

    int insertSelective(Role record);

    List<Role> selectByExample(RoleExample example);

    Role selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);
}