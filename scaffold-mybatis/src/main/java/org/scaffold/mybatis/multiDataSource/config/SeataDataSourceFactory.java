package org.scaffold.mybatis.multiDataSource.config;

import com.alibaba.druid.pool.DruidDataSource;
import io.seata.rm.datasource.DataSourceProxy;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.scaffold.mybatis.multiDataSource.constant.DBConstant;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


/**
 * @description : 多数据源配置
 */

//@MapperScan(basePackages = {"org.**.dao","org.**.mapper"},
//        sqlSessionTemplateRef = "sqlSessionTemplate")
@EnableConfigurationProperties(MybatisProperties.class)
public class SeataDataSourceFactory {


    /***
     * 创建 DruidXADataSource 1 用@ConfigurationProperties自动配置属性
     */
    @Bean("originMaster")
    @ConfigurationProperties("spring.datasource.druid.master")
    public DataSource originMaster() {
        return new DruidDataSource();
    }

    /***
     * 创建 DruidXADataSource 2
     */
    @Bean("originSlave1")
    @ConfigurationProperties("spring.datasource.druid.slave1")
    @ConditionalOnProperty(prefix = "spring.datasource.druid.slave1", name = "enabled", havingValue = "true")
    public DataSource originSlave1() {
        return new DruidDataSource();
    }

    /***
     * 创建 DruidXADataSource 3
     */
    @Bean("originSlave2")
    @ConfigurationProperties("spring.datasource.druid.slave2")
    @ConditionalOnProperty(prefix = "spring.datasource.druid.slave2", name = "enabled", havingValue = "true")
    public DataSource originSlave2() {
        return new DruidDataSource();
    }

    /***
     * 创建 DruidXADataSource 3
     */
    @Bean("originSlave3")
    @ConfigurationProperties("spring.datasource.druid.slave3")
    @ConditionalOnProperty(prefix = "spring.datasource.druid.slave3", name =   "enabled", havingValue = "true")
    public DataSource originSlave3() {
        return new DruidDataSource();
    }


    /***
     * 创建 DruidXADataSource 3
     */
    @Bean("originSlave4")
    @ConfigurationProperties("spring.datasource.druid.slave4")
    @ConditionalOnProperty(prefix = "spring.datasource.druid.slave4", name = "enabled", havingValue = "true")
    public DataSource originSlave4() {
        return new DruidDataSource();
    }


    /***
     * 创建 DruidXADataSource 1 用@ConfigurationProperties自动配置属性
     */
    @Bean("dataSourceMaster")
    @Primary
    public DataSource dataSourceMaster(@Qualifier("originMaster") DataSource dataSource) {
        return new DataSourceProxy(dataSource);
    }

    /***
     * 创建 DruidXADataSource 2
     */
    @Bean("dataSourceSlave1")
    @ConditionalOnProperty(prefix = "spring.datasource.druid.slave1", name = "enabled", havingValue = "true")
    public DataSource dataSourceSlave1(@Qualifier("originSlave1") DataSource dataSource) {
      return  new DataSourceProxy(dataSource);
    }

    /***
     * 创建 DruidXADataSource 2
     */
    @Bean("dataSourceSlave2")
    @ConditionalOnProperty(prefix = "spring.datasource.druid.slave2", name =
            "enabled", havingValue = "true")
    public DataSource dataSourceSlave2(@Qualifier("originSlave2") DataSource dataSource) {
        return  new DataSourceProxy(dataSource);
    }

    /***
     * 创建 DruidXADataSource 2
     */
    @Bean("dataSourceSlave3")
    @ConditionalOnProperty(prefix = "spring.datasource.druid.slave3", name =
            "enabled", havingValue = "true")
    public DataSource dataSourceSlave3(@Qualifier("originSlave3") DataSource dataSource) {
        return  new DataSourceProxy(dataSource);
    }

    /***
     * 创建 DruidXADataSource 2
     */
    @Bean("dataSourceSlave4")
    @ConditionalOnProperty(prefix = "spring.datasource.druid.slave4", name =
            "enabled", havingValue = "true")
    public DataSource dataSourceSlave4(@Qualifier("originSlave4") DataSource dataSource) {
        return  new DataSourceProxy(dataSource);
    }




    /**
     * @param dataSourceMaster 数据源1
     * @return 数据源1的会话工厂
     */
    @Bean("sqlSessionFactoryMaster")
    @Primary
    public SqlSessionFactory sqlSessionFactoryMaster(@Qualifier("dataSourceMaster") DataSource dataSourceMaster, MybatisProperties mybatisProperties)
            throws Exception {
        return createSqlSessionFactory(dataSourceMaster, mybatisProperties);
    }


    /**
     * @param dataSourceSlave1 数据源1
     * @return 数据源2的会话工厂
     */
    @Bean("sqlSessionFactorySlave1")
    @ConditionalOnProperty(prefix = "spring.datasource.druid.slave1", name = "enabled", havingValue = "true")
    public SqlSessionFactory sqlSessionFactorySlave1(@Qualifier("dataSourceSlave1") DataSource dataSourceSlave1, MybatisProperties mybatisProperties)
            throws Exception {
        return createSqlSessionFactory(dataSourceSlave1, mybatisProperties);
    }

    /**
     * @param dataSourceSlave2 数据源2
     * @return 数据源2的会话工厂
     */
    @Bean("sqlSessionFactorySlave2")
    @ConditionalOnProperty(prefix = "spring.datasource.druid.slave2", name = "enabled", havingValue = "true")
    public SqlSessionFactory sqlSessionFactorySlave2(@Qualifier("dataSourceSlave2") DataSource dataSourceSlave2, MybatisProperties mybatisProperties)
            throws Exception {
        return createSqlSessionFactory(dataSourceSlave2, mybatisProperties);
    }

    /**
     * @param dataSourceSlave3 数据源2
     * @return 数据源2的会话工厂
     */
    @Bean("sqlSessionFactorySlave3")
    @ConditionalOnProperty(prefix = "spring.datasource.druid.slave3", name = "enabled", havingValue = "true")
    public SqlSessionFactory sqlSessionFactorySlave3(@Qualifier("dataSourceSlave3") DataSource dataSourceSlave3,MybatisProperties mybatisProperties)
            throws Exception {
        return createSqlSessionFactory(dataSourceSlave3, mybatisProperties);
    }

    /**
     * @param dataSourceSlave4 数据源2
     * @return 数据源2的会话工厂
     */
    @Bean("sqlSessionFactorySlave4")
    @ConditionalOnProperty(prefix = "spring.datasource.druid.slave4", name = "enabled", havingValue = "true")
    public SqlSessionFactory sqlSessionFactorySlave4(@Qualifier("dataSourceSlave4") DataSource dataSourceSlave4,MybatisProperties mybatisProperties)
            throws Exception {
        return createSqlSessionFactory(dataSourceSlave4, mybatisProperties);
    }

    /***
     * sqlSessionTemplate与Spring事务管理一起使用，以确保使用的实际SqlSession是与当前Spring事务关联的,
     * 此外它还管理会话生命周期，包括根据Spring事务配置根据需要关闭，提交或回滚会话
     * @param sqlSessionFactoryMaster 数据源1
     */
    @Bean
    public CustomSqlSessionTemplate sqlSessionTemplate(@Qualifier("sqlSessionFactoryMaster") SqlSessionFactory sqlSessionFactoryMaster) {

        Map<Object, SqlSessionFactory> sqlSessionFactoryMap = new HashMap<>();

        sqlSessionFactoryMap.put(DBConstant.MASTER, sqlSessionFactoryMaster);

        setSqlSession(sqlSessionFactoryMap, DBConstant.SLAVE1,"sqlSessionFactorySlave1");

        setSqlSession(sqlSessionFactoryMap, DBConstant.SLAVE2,"sqlSessionFactorySlave2");

        setSqlSession(sqlSessionFactoryMap, DBConstant.SLAVE3,"sqlSessionFactorySlave3");

        setSqlSession(sqlSessionFactoryMap, DBConstant.SLAVE4, "sqlSessionFactorySlave4");

        CustomSqlSessionTemplate customSqlSessionTemplate = new CustomSqlSessionTemplate(sqlSessionFactoryMaster);

        customSqlSessionTemplate.setTargetSqlSessionFactories(sqlSessionFactoryMap);

        return customSqlSessionTemplate;
    }


    /***
     * 自定义会话工厂
     * @param dataSource 数据源
     * @return :自定义的会话工厂
     */
    private SqlSessionFactory createSqlSessionFactory(DataSource dataSource, MybatisProperties mybatisProperties) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        //factoryBean.setVfs(SpringBootVFS.class);
        factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mybatisProperties.getMapperLocations() != null ?
                mybatisProperties.getMapperLocations()[0] : "classpath*:mapper/*.xml"));

        factoryBean.setMapperLocations(mybatisProperties.resolveMapperLocations());
        factoryBean.setTypeAliasesPackage(mybatisProperties.getTypeAliasesPackage());
        factoryBean.setConfigurationProperties(mybatisProperties.getConfigurationProperties());
        factoryBean.setConfigLocation(Optional.ofNullable(mybatisProperties.getConfigLocation()).map(location -> {
            try {
                ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
                return resourceResolver.getResource(location);
            } catch (Exception var3) {
                return null;
            }
        }).orElse(null));
        factoryBean.setConfiguration(mybatisProperties.getConfiguration());

        return factoryBean.getObject();
    }

    /**
     * 设置数据源
     *
     * @param targetsetSqlSessions 备选数据源集合
     * @param sourceName 数据源名称
     * @param beanName bean名称
     */
    public void setSqlSession(Map<Object, SqlSessionFactory> targetsetSqlSessions, String sourceName, String beanName)
    {
        try
        {
            SqlSessionFactory sqlSessionFactory = SpringUtils.getBean(beanName);
            targetsetSqlSessions.put(sourceName, sqlSessionFactory);
        }
        catch (Exception e)
        {
        }
    }
}
