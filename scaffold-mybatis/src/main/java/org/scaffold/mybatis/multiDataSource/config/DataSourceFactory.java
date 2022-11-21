package org.scaffold.mybatis.multiDataSource.config;

import com.alibaba.druid.pool.xa.DruidXADataSource;
import com.atomikos.jdbc.AtomikosDataSourceBean;
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
//@MapperScan(basePackages = {"org.**.dao", "org.**.mapper"},
//        sqlSessionTemplateRef = "sqlSessionTemplate")
@EnableConfigurationProperties(MybatisProperties.class)
public class DataSourceFactory {


    /***
     * 创建 DruidXADataSource 1 用@ConfigurationProperties自动配置属性
     */
    @Bean("druidDataSourceMaster")
    @ConfigurationProperties("spring.datasource.druid.master")
    public DataSource druidDataSourceMaster() {
        return new DruidXADataSource();
    }

    /***
     * 创建 DruidXADataSource 2
     */
    @Bean("druidDataSourceSlave1")
    @ConfigurationProperties("spring.datasource.druid.slave1")
    @ConditionalOnProperty(prefix = "spring.datasource.druid.slave1", name = "enabled", havingValue = "true")
    public DataSource druidDataSourceSlave1() {
        return new DruidXADataSource();
    }

    /***
     * 创建 DruidXADataSource 3
     */
    @Bean("druidDataSourceSlave2")
    @ConfigurationProperties("spring.datasource.druid.slave2")
    @ConditionalOnProperty(prefix = "spring.datasource.druid.slave2", name = "enabled", havingValue = "true")
    public DataSource druidDataSourceSlave2() {
        return new DruidXADataSource();
    }

    /***
     * 创建 DruidXADataSource 4
     */
    @Bean("druidDataSourceSlave3")
    @ConfigurationProperties("spring.datasource.druid.slave3")
    @ConditionalOnProperty(prefix = "spring.datasource.druid.slave3", name = "enabled", havingValue = "true")
    public DataSource druidDataSourceSlave3() {
        return new DruidXADataSource();
    }

    /***
     * 创建 DruidXADataSource 5
     */
    @Bean("druidDataSourceSlave4")
    @ConfigurationProperties("spring.datasource.druid.slave4")
    @ConditionalOnProperty(prefix = "spring.datasource.druid.slave4", name = "enabled", havingValue = "true")
    public DataSource druidDataSourceSlave4() {
        return new DruidXADataSource();
    }


    /**
     * 创建支持XA事务的Atomikos数据源1
     */
    @Bean("dataSourceMaster")
    public DataSource dataSourceMaster(@Qualifier("druidDataSourceMaster") DataSource druidDataSourceMaster) {
        AtomikosDataSourceBean sourceBean = new AtomikosDataSourceBean();
        sourceBean.setXaDataSource((DruidXADataSource) druidDataSourceMaster);
        sourceBean.setMaxPoolSize(((DruidXADataSource) druidDataSourceMaster).getMaxActive());
        sourceBean.setMinPoolSize(((DruidXADataSource) druidDataSourceMaster).getMinIdle());
        sourceBean.setMaxLifetime((int) ((DruidXADataSource) druidDataSourceMaster).getMaxWait());
        sourceBean.setTestQuery(((DruidXADataSource) druidDataSourceMaster).getValidationQuery());
        sourceBean.setBorrowConnectionTimeout((int) (((DruidXADataSource) druidDataSourceMaster).getMinEvictableIdleTimeMillis() / 1000));
        // 必须为数据源指定唯一标识
        sourceBean.setUniqueResourceName("master");
        return sourceBean;
    }

    /**
     * 创建支持XA事务的Atomikos数据源2
     */
    @Bean("dataSourceSlave1")
    @ConditionalOnProperty(prefix = "spring.datasource.druid.slave1", name = "enabled", havingValue = "true")
    public DataSource dataSourceSlave1(@Qualifier("druidDataSourceSlave1") DataSource druidDataSourceSlave1) {
        AtomikosDataSourceBean sourceBean = new AtomikosDataSourceBean();
        sourceBean.setXaDataSource((DruidXADataSource) druidDataSourceSlave1);
        sourceBean.setMaxPoolSize(((DruidXADataSource) druidDataSourceSlave1).getMaxActive());
        sourceBean.setMinPoolSize(((DruidXADataSource) druidDataSourceSlave1).getMinIdle());
        sourceBean.setMaxLifetime((int) ((DruidXADataSource) druidDataSourceSlave1).getMaxWait());
        sourceBean.setTestQuery(((DruidXADataSource) druidDataSourceSlave1).getValidationQuery());
        sourceBean.setBorrowConnectionTimeout((int) (((DruidXADataSource) druidDataSourceSlave1).getMinEvictableIdleTimeMillis() / 1000));
        sourceBean.setUniqueResourceName("slave1");
        return sourceBean;
    }

    /**
     * 创建支持XA事务的Atomikos数据源3
     */
    @Bean("dataSourceSlave2")
    @ConditionalOnProperty(prefix = "spring.datasource.druid.slave2", name = "enabled", havingValue = "true")
    public DataSource dataSourceSlave2(@Qualifier("druidDataSourceSlave2") DataSource druidDataSourceSlave2) {
        AtomikosDataSourceBean sourceBean = new AtomikosDataSourceBean();
        sourceBean.setMaxPoolSize(((DruidXADataSource) druidDataSourceSlave2).getMaxActive());
        sourceBean.setMinPoolSize(((DruidXADataSource) druidDataSourceSlave2).getMinIdle());
        sourceBean.setMaxLifetime((int) ((DruidXADataSource) druidDataSourceSlave2).getMaxWait());
        sourceBean.setBorrowConnectionTimeout((int) (((DruidXADataSource) druidDataSourceSlave2).getMinEvictableIdleTimeMillis() / 1000));
        sourceBean.setTestQuery(((DruidXADataSource) druidDataSourceSlave2).getValidationQuery());
        sourceBean.setXaDataSource((DruidXADataSource) druidDataSourceSlave2);
        sourceBean.setUniqueResourceName("slave2");
        return sourceBean;
    }

    /**
     * 创建支持XA事务的Atomikos数据源3
     */
    @Bean("dataSourceSlave3")
    @ConditionalOnProperty(prefix = "spring.datasource.druid.slave3", name =
            "enabled", havingValue = "true")
    public DataSource dataSourceSlave3(@Qualifier("druidDataSourceSlave3") DataSource druidDataSourceSlave3) {
        AtomikosDataSourceBean sourceBean = new AtomikosDataSourceBean();
        sourceBean.setMaxPoolSize(((DruidXADataSource) druidDataSourceSlave3).getMaxActive());
        sourceBean.setMinPoolSize(((DruidXADataSource) druidDataSourceSlave3).getMinIdle());
        sourceBean.setMaxLifetime((int) ((DruidXADataSource) druidDataSourceSlave3).getMaxWait());
        sourceBean.setBorrowConnectionTimeout((int) (((DruidXADataSource) druidDataSourceSlave3).getMinEvictableIdleTimeMillis() / 1000));
        sourceBean.setTestQuery(((DruidXADataSource) druidDataSourceSlave3).getValidationQuery());
        sourceBean.setXaDataSource((DruidXADataSource) druidDataSourceSlave3);
        sourceBean.setUniqueResourceName("slave3");
        return sourceBean;
    }

    /**
     * 创建支持XA事务的Atomikos数据源3
     */
    @Bean("dataSourceSlave4")
    @ConditionalOnProperty(prefix = "spring.datasource.druid.slave4", name =
            "enabled", havingValue = "true")
    public DataSource dataSourceSlave4(@Qualifier("druidDataSourceSlave4") DataSource druidDataSourceSlave4) {
        AtomikosDataSourceBean sourceBean = new AtomikosDataSourceBean();
        sourceBean.setMaxPoolSize(((DruidXADataSource) druidDataSourceSlave4).getMaxActive());
        sourceBean.setMinPoolSize(((DruidXADataSource) druidDataSourceSlave4).getMinIdle());
        sourceBean.setMaxLifetime((int) ((DruidXADataSource) druidDataSourceSlave4).getMaxWait());
        sourceBean.setBorrowConnectionTimeout((int) (((DruidXADataSource) druidDataSourceSlave4).getMinEvictableIdleTimeMillis() / 1000));
        sourceBean.setTestQuery(((DruidXADataSource) druidDataSourceSlave4).getValidationQuery());
        sourceBean.setXaDataSource((DruidXADataSource) druidDataSourceSlave4);
        sourceBean.setUniqueResourceName("slave4");
        return sourceBean;
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
    @ConditionalOnProperty(prefix = "spring.datasource.druid.slave3", name =
            "enabled", havingValue = "true")
    public SqlSessionFactory sqlSessionFactorySlave3(@Qualifier("dataSourceSlave3") DataSource dataSourceSlave3, MybatisProperties mybatisProperties)
            throws Exception {
        return createSqlSessionFactory(dataSourceSlave3, mybatisProperties);
    }

    /**
     * @param dataSourceSlave4 数据源2
     * @return 数据源2的会话工厂
     */
    @Bean("sqlSessionFactorySlave4")
    @ConditionalOnProperty(prefix = "spring.datasource.druid.slave4", name ="enabled", havingValue = "true")
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
