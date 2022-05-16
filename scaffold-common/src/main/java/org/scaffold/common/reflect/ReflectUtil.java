/**
 * Author : chiziyue
 * Date : 2022年5月15日 下午8:35:57
 * Title : org.scaffold.common.reflect.ReflectUtil.java
 *
**/
package org.scaffold.common.reflect;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

import org.scaffold.common.annotation.ReflectionIgnore;

public class ReflectUtil {
	/**
	 * 获取指定CLASS的属性,包括上级类
	 * 
	 * @param clazz
	 * @return Field[]
	 */
	public static Field[] getFields(Class<?> clazz) {
		List<Field> list = getFieldList(clazz);
		return list.toArray(new Field[list.size()]);
	}
	
	/**
	 * 获取指定CLASS的属性,包括上级类
	 * 
	 * @param clazz
	 * @return List<Field>
	 */
	public static List<Field> getFieldList(Class<?> clazz) {
		LinkedList<Field> list = new LinkedList<>();
		for (Class<?> superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
			Field[] fields = superClass.getDeclaredFields();
			for (Field field : fields) {
				if (field.getAnnotation(ReflectionIgnore.class) == null) {
					list.add(field);
				}
			}
		}
		return list;
	}
}
