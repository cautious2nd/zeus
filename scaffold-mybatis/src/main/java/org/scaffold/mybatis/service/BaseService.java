package org.scaffold.mybatis.service;

import java.util.List;

public interface BaseService<T> {

    public int insert(T t);

    public int update(T t);

    public int delete(T t);

    public T findByKey(T t);

    public List<T> findByWhere(T t);

}
