/**
 * Author : chiziyue
 * Date : 2022年5月15日 下午8:35:57
 * Title : org.scaffold.common.reflect.ReflectUtil.java
 **/
package org.scaffold.common.reflect;

import org.scaffold.common.annotation.ReflectionIgnore;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.LinkedList;
import java.util.List;

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

    /**
     * 直接设置对象属性值, 无视private/protected修饰符, 不经过setter函数.final参数直接跳过
     */
    public static void setFieldValue(final Object obj, final String fieldName, final Object value) {
        Field field = getAccessibleField(obj, fieldName);
        if (field == null) {
            throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + obj + "]");
        }
        if (Modifier.isFinal(field.getModifiers()))
            return;
        try {
            field.set(obj, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static Object getFieldValue(final Object obj, final String fieldName) {
        Field field = getAccessibleField(obj, fieldName);

        if (field == null) {
            throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + obj + "]");
        }

        Object result = null;
        try {
            result = field.get(obj);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 循环向上转型, 获取对象的DeclaredField, 并强制设置为可访问.
     * <p>
     * 如向上转型到Object仍无法找到, 返回null.
     */
    public static Field getAccessibleField(final Object obj, final String fieldName) {
        if (null == obj) {
            throw new RuntimeException("object can't be null");
        }

        if (null == fieldName || "".equals(fieldName.trim())) {
            throw new RuntimeException("fieldName can't be blank");
        }
        for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass
                .getSuperclass()) {
            try {
                Field field = superClass.getDeclaredField(fieldName);
                makeAccessible(field);
                return field;
            } catch (NoSuchFieldException e) {// NOSONAR
                // Field不在当前类定义,继续向上转型
                continue;// new add
            }
        }
        return null;
    }

    /**
     * 改变private/protected的成员变量为public,尽量不调用实际改动的语句,避免JDK的SecurityManager.
     */
    private static void makeAccessible(Field field) {
        if ((!Modifier.isPublic(field.getModifiers()) || !Modifier.isPublic(field.getDeclaringClass().getModifiers())
                || Modifier.isFinal(field.getModifiers())) && !field.isAccessible()) {
            field.setAccessible(true);
        }
    }

    /**
     * 改变private/protected的方法为public,尽量不调用实际改动的语句,避免JDK的SecurityManager.
     */
    private static void makeAccessible(Method method) {
        if ((!Modifier.isPublic(method.getModifiers()) || !Modifier.isPublic(method.getDeclaringClass().getModifiers())
                || Modifier.isFinal(method.getModifiers())) && !method.isAccessible()) {
            method.setAccessible(true);
        }
    }


}
