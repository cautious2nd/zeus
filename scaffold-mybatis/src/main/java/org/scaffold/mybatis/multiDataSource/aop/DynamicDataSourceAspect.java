package org.scaffold.mybatis.multiDataSource.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.scaffold.mybatis.multiDataSource.config.DataSourceContextHolder;
import org.scaffold.mybatis.multiDataSource.config.ZeusDataSource;
import org.scaffold.mybatis.multiDataSource.constant.DBConstant;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author : heibaiying
 * @description : 动态方式去切换数据源
 */
@Aspect
@Component
public class DynamicDataSourceAspect {
    @Pointcut(value = "@annotation(org.scaffold.mybatis.multiDataSource.config.ZeusDataSource)")
    public void dataSourcePointCut() {
    }

    @Before(value = "dataSourcePointCut()")
    public void beforeSwitchDS(JoinPoint point) {

        //获得当前访问的class
        Class<?> className = point.getTarget().getClass();

        //获得访问的方法名
        String methodName = point.getSignature().getName();
        //得到方法的参数的类型
        Class[] argClass = ((MethodSignature)point.getSignature()).getParameterTypes();
        String dataSource = DBConstant.MASTER;
        try {
            // 得到访问的方法对象
            Method method = className.getMethod(methodName, argClass);

            // 判断是否存在@DS注解
            if (method.isAnnotationPresent(ZeusDataSource.class)) {
                ZeusDataSource zeusDataSource = method.getAnnotation(ZeusDataSource.class);
                // 取出注解中的数据源名
                dataSource = zeusDataSource.value();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        DataSourceContextHolder.setDataSourceKey(dataSource);
    }

    @After(value = "dataSourcePointCut()")
    public void afterSwitchDS(JoinPoint point) {
        DataSourceContextHolder.clearDataSourceKey();
    }
}
