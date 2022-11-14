/**
 * Author : czy
 * Date : 2019年8月28日 下午4:37:00
 * Title : org.scaffold.common.json.annotation.IgnorWrite.java
 *
**/
package org.scaffold.common.json.annotation;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface IgnorWrite {

}
