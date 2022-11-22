/**
 * Author : chiziyue
 * Date : 2022年3月30日 上午10:18:23
 * Title : org.scaffold.common.annotation.AnnotationUtils.java
 **/
package org.scaffold.common.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class AnnotationUtils {

    public static Object getAnnotationValue(Class<?> clazz, Class<? extends Annotation> annotationClass) {
        return getAnnotationValue(clazz, annotationClass, null);
    }

    public static Object getAnnotationValue(Class<?> clazz, Class<? extends Annotation> annotationClass,
                                            String methodName) {
        if (null == methodName) {
            methodName = "value";
        }
        return reflectValue(clazz, annotationClass, methodName);
    }

    public static Object getAnnotationValue(Field field, Class<? extends Annotation> clazz) {
        return getAnnotationValue(field, clazz, null);
    }

    public static Object getAnnotationValue(Field field, Class<? extends Annotation> clazz, String methodName) {
        if (null == methodName) {
            methodName = "value";
        }
        return reflectValue(field, clazz, methodName);
    }

    private static Object reflectValue(Class<?> clazz, Class<? extends Annotation> annotationClass, String methodName) {
        Annotation temp = clazz.getAnnotation(annotationClass);

        Method method = null;
        if (null == temp) {
            return null;
        }
        try {
            method = annotationClass.getDeclaredMethod(methodName, null);
            return method.invoke(temp, new Object[]{});
        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    private static Object reflectValue(Field field, Class<? extends Annotation> clazz, String methodName) {
        Annotation temp = field.getAnnotation(clazz);

        Method method = null;
        if (null == temp) {
            return null;
        }

        try {

            method = clazz.getDeclaredMethod(methodName, null);
            return method.invoke(temp, new Object[]{});
        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

}
