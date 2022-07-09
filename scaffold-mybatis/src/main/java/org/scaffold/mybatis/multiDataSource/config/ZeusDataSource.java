package org.scaffold.mybatis.multiDataSource.config;



import org.scaffold.mybatis.multiDataSource.constant.DBConstant;

import java.lang.annotation.*;

/**
 * @author ：zjd
 * @date ：Created By 2022/4/16 11:51
 * @description：${description}
 * @modified By：
 * @version: $version$
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({
        ElementType.METHOD,
        ElementType.TYPE
})
@Inherited
public @interface ZeusDataSource {
    String value() default DBConstant.MASTER;
}
