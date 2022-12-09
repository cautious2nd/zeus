/**
 * Author : czy
 * Date : 2019年8月28日 下午3:50:23
 * Title : org.scaffold.common.json.annotation.IgnorRead.java
 *
**/
package org.scaffold.common.json.annotation;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface IgnorRead {

}
