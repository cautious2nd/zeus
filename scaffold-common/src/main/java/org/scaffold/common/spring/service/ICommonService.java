package org.scaffold.common.spring.service;

import java.util.List;

public interface ICommonService<T> {
    public int insert(T t);

    public int update(T t);

    public T findByKey(T t);

    public List<T> findByWhere(T t);
}
