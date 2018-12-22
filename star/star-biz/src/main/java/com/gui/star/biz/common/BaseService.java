package com.gui.star.biz.common;

import java.util.List;

public interface BaseService<T> {

    List<T> findAll();

    T findById(Long id);

    int save(T t);

    int update(T t);

    int remove(Long id);
}
