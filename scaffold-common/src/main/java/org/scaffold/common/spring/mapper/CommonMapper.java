package org.scaffold.common.spring.mapper;

import java.util.List;

public interface CommonMapper<T> {
    public int insert(T t);

    public int delete(T t);

    public int update(T t);

    public T findByKey(T t);

    public List<T> findByWhere(T t);
}
