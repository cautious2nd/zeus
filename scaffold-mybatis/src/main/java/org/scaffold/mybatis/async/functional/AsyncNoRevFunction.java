package org.scaffold.mybatis.async.functional;

/**
 * 对应没有返回值的类型
 * @param <T> 礼貌性有个T
 */
@FunctionalInterface
public interface AsyncNoRevFunction<T> {
    void apply() throws Exception;
}
