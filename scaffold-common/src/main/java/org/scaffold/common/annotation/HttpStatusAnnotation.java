/**
 * Author : chiziyue
 * Date : 2020年3月20日 下午2:09:31
 * Title : org.scaffold.common.annotation.HttpStatusAnnotation.java
 *
**/
package org.scaffold.common.annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface HttpStatusAnnotation {
	int value();
}