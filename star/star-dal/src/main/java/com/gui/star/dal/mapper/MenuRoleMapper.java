package com.gui.star.dal.mapper;

import com.gui.star.dal.model.MenuRole;
import com.gui.star.dal.model.MenuRoleExample;
import java.util.List;

public interface MenuRoleMapper {
    int countByExample(MenuRoleExample example);

    int deleteByPrimaryKey(Long id);

    int insert(MenuRole record);

    int insertSelective(MenuRole record);

    List<MenuRole> selectByExample(MenuRoleExample example);

    MenuRole selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MenuRole record);

    int updateByPrimaryKey(MenuRole record);
}