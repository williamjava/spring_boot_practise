package com.gui.star.biz.common;

import java.util.List;

import com.gui.star.dal.common.CommonMapper;

public abstract class BaseServiceImpl<T> implements BaseService<T> {

    public abstract CommonMapper<T> getMapper();

    @Override
    public List<T> findAll() {
        return getMapper().selectAll();
    }

    @Override
    public T findById(Long id) {
        return getMapper().selectByPrimaryKey(id);
    }

    @Override
    public int save(T t) {
         return getMapper().insert(t);
    }

    @Override
    public int remove(Long id) {
        return getMapper().deleteByPrimaryKey(id);
    }

    @Override
    public int update(T t) {
        return getMapper().updateByPrimaryKey(t);
    }
}
