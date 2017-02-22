package com.gui.star.dal.mapper;

import com.gui.star.dal.model.PermissionRole;
import com.gui.star.dal.model.PermissionRoleExample;
import java.util.List;

public interface PermissionRoleMapper {
    int countByExample(PermissionRoleExample example);

    int deleteByPrimaryKey(Long id);

    int insert(PermissionRole record);

    int insertSelective(PermissionRole record);

    List<PermissionRole> selectByExample(PermissionRoleExample example);

    PermissionRole selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PermissionRole record);

    int updateByPrimaryKey(PermissionRole record);
}