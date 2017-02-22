package com.gui.star.dal.mapper;

import com.gui.star.dal.model.Menu;
import com.gui.star.dal.model.MenuExample;
import java.util.List;

public interface MenuMapper {
    int countByExample(MenuExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Menu record);

    int insertSelective(Menu record);

    List<Menu> selectByExample(MenuExample example);

    Menu selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Menu record);

    int updateByPrimaryKey(Menu record);
}