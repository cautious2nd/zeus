package org.scaffold.mybatis.async.functional;

/**
 * 对应有返回值的function
 * @param <T> 返回值类型
 */
@FunctionalInterface
public interface AsyncRevFunction<T> {
    T apply() throws Exception;
}
