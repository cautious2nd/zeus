/**
 * Author : chiziyue
 * Date : 2021年8月20日 上午12:10:55
 * Title : org.scaffold.common.annotation.TokenIgnore.java
 *
**/
package org.scaffold.common.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Documented
@Retention(RUNTIME)
@Target(METHOD)
public @interface TokenIgnore {

}
