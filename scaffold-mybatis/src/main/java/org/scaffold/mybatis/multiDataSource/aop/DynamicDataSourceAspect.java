package org.scaffold.mybatis.multiDataSource.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.scaffold.mybatis.multiDataSource.config.DataSourceContextHolder;
import org.scaffold.mybatis.multiDataSource.config.ZeusDataSource;
import org.scaffold.mybatis.multiDataSource.constant.DBConstant;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;

/**
 * @author : heibaiying
 * @description : 动态方式去切换数据源
 */
@Aspect
@Order(1)
@Component
public class DynamicDataSourceAspect {
    @Pointcut("@annotation(org.scaffold.mybatis.multiDataSource.config.ZeusDataSource)"
            + "|| @within(org.scaffold.mybatis.multiDataSource.config.ZeusDataSource)")
    public void dataSourcePointCut() {
    }

    @Before(value = "dataSourcePointCut()")
    public void beforeSwitchDS(JoinPoint point) {
        ZeusDataSource zeusDataSource=getDataSource(point);
        if(!DBConstant.dBConstants.contains(zeusDataSource.value())){
            DataSourceContextHolder.setDataSourceKey(DBConstant.MASTER);
        }else{

            DataSourceContextHolder.setDataSourceKey(zeusDataSource.value());
        }
    }

    @After(value = "dataSourcePointCut()")
    public void afterSwitchDS(JoinPoint point) {
        DataSourceContextHolder.clearDataSourceKey();
    }

    /**
     * 获取需要切换的数据源
     */
    public ZeusDataSource getDataSource(JoinPoint point)
    {
        MethodSignature signature = (MethodSignature) point.getSignature();
        ZeusDataSource zeusDataSource =
                AnnotationUtils.findAnnotation(signature.getMethod(), ZeusDataSource.class);
        if (Objects.nonNull(zeusDataSource))
        {
            return zeusDataSource;
        }

        return AnnotationUtils.findAnnotation(signature.getDeclaringType(), ZeusDataSource.class);
    }
}
