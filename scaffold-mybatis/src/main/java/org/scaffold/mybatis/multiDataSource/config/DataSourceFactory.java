package org.scaffold.mybatis.multiDataSource.config;

import com.alibaba.druid.pool.xa.DruidXADataSource;
import com.atomikos.jdbc.AtomikosDataSourceBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.scaffold.mybatis.multiDataSource.constant.DBConstant;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


/**
 * @author : heibaiying
 * @description : 多数据源配置
 */
@Configuration
@MapperScan(basePackages = {"org.*.*.mapper", "org.*.*.dao"}, sqlSessionTemplateRef = "sqlSessionTemplate")
@EnableConfigurationProperties(MybatisProperties.class)
public class DataSourceFactory {


    /***
     * 创建 DruidXADataSource 1 用@ConfigurationProperties自动配置属性
     */
    @Bean
    @ConfigurationProperties("spring.datasource.druid.master")
    public DataSource druidDataSourceMaster() {
        return new DruidXADataSource();
    }

    /***
     * 创建 DruidXADataSource 2
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.druid.slave1", ignoreInvalidFields = true)
    public DataSource druidDataSourceSlave1() {
        return new DruidXADataSource();
    }

    /***
     * 创建 DruidXADataSource 3
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.druid.slave2", ignoreInvalidFields = true)
    public DataSource druidDataSourceSlave2() {
        return new DruidXADataSource();
    }


    /**
     * 创建支持XA事务的Atomikos数据源1
     */
    @Bean("dataSourceMaster")
    public DataSource dataSourceMaster(DataSource druidDataSourceMaster) {
        AtomikosDataSourceBean sourceBean = new AtomikosDataSourceBean();
        sourceBean.setXaDataSource((DruidXADataSource) druidDataSourceMaster);
        sourceBean.setMaxPoolSize(((DruidXADataSource) druidDataSourceMaster).getMaxActive());
        sourceBean.setMinPoolSize(((DruidXADataSource) druidDataSourceMaster).getMinIdle());
        sourceBean.setMaxLifetime((int) ((DruidXADataSource) druidDataSourceMaster).getMaxWait());
        sourceBean.setTestQuery(((DruidXADataSource) druidDataSourceMaster).getValidationQuery());
        sourceBean.setBorrowConnectionTimeout((int)(((DruidXADataSource) druidDataSourceMaster).getMinEvictableIdleTimeMillis()/1000));
        // 必须为数据源指定唯一标识
        sourceBean.setUniqueResourceName("master");
        return sourceBean;
    }

    /**
     * 创建支持XA事务的Atomikos数据源2
     */
    @Bean("dataSourceSlave1")
    public DataSource dataSourceSlave1(DataSource druidDataSourceSlave1) {
        AtomikosDataSourceBean sourceBean = new AtomikosDataSourceBean();
        sourceBean.setXaDataSource((DruidXADataSource) druidDataSourceSlave1);
        sourceBean.setMaxPoolSize(((DruidXADataSource) druidDataSourceSlave1).getMaxActive());
        sourceBean.setMinPoolSize(((DruidXADataSource) druidDataSourceSlave1).getMinIdle());
        sourceBean.setMaxLifetime((int) ((DruidXADataSource) druidDataSourceSlave1).getMaxWait());
        sourceBean.setTestQuery(((DruidXADataSource) druidDataSourceSlave1).getValidationQuery());
        sourceBean.setBorrowConnectionTimeout((int)(((DruidXADataSource) druidDataSourceSlave1).getMinEvictableIdleTimeMillis()/1000));
        sourceBean.setUniqueResourceName("slave1");
        return sourceBean;
    }

    /**
     * 创建支持XA事务的Atomikos数据源3
     */
    @Bean("dataSourceSlave2")
    public DataSource dataSourceSlave2(DataSource druidDataSourceSlave2) {
        AtomikosDataSourceBean sourceBean = new AtomikosDataSourceBean();
        sourceBean.setMaxPoolSize(((DruidXADataSource) druidDataSourceSlave2).getMaxActive());
        sourceBean.setMinPoolSize(((DruidXADataSource) druidDataSourceSlave2).getMinIdle());
        sourceBean.setMaxLifetime((int) ((DruidXADataSource) druidDataSourceSlave2).getMaxWait());
        sourceBean.setBorrowConnectionTimeout((int)(((DruidXADataSource) druidDataSourceSlave2).getMinEvictableIdleTimeMillis()/1000));
        sourceBean.setTestQuery(((DruidXADataSource) druidDataSourceSlave2).getValidationQuery());
        sourceBean.setXaDataSource((DruidXADataSource) druidDataSourceSlave2);
        sourceBean.setUniqueResourceName("slave2");
        return sourceBean;
    }


    /**
     * @param dataSourceMaster 数据源1
     * @return 数据源1的会话工厂
     */
    @Bean("sqlSessionFactoryMaster")
    public SqlSessionFactory sqlSessionFactoryMaster(DataSource dataSourceMaster, MybatisProperties mybatisProperties)
            throws Exception {
        return createSqlSessionFactory(dataSourceMaster, mybatisProperties);
    }


    /**
     * @param dataSourceSlave1 数据源1
     * @return 数据源2的会话工厂
     */
    @Bean("sqlSessionFactorySlave1")
    public SqlSessionFactory sqlSessionFactorySlave1(DataSource dataSourceSlave1, MybatisProperties mybatisProperties)
            throws Exception {
        return createSqlSessionFactory(dataSourceSlave1, mybatisProperties);
    }

    /**
     * @param dataSourceSlave2 数据源2
     * @return 数据源2的会话工厂
     */
    @Bean("sqlSessionFactorySlave2")
    public SqlSessionFactory sqlSessionFactorySlave2(DataSource dataSourceSlave2, MybatisProperties mybatisProperties)
            throws Exception {
        return createSqlSessionFactory(dataSourceSlave2, mybatisProperties);
    }


    /***
     * sqlSessionTemplate与Spring事务管理一起使用，以确保使用的实际SqlSession是与当前Spring事务关联的,
     * 此外它还管理会话生命周期，包括根据Spring事务配置根据需要关闭，提交或回滚会话
     * @param sqlSessionFactoryMaster 数据源1
     * @param sqlSessionFactorySlave1 数据源2
     *@param sqlSessionFactorySlave2 数据源3
     */
    @Bean
    public CustomSqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactoryMaster,
                                                       SqlSessionFactory sqlSessionFactorySlave1,
                                                       SqlSessionFactory sqlSessionFactorySlave2) {
        Map<Object, SqlSessionFactory> sqlSessionFactoryMap = new HashMap<>();
        sqlSessionFactoryMap.put(DBConstant.MASTER, sqlSessionFactoryMaster);
        sqlSessionFactoryMap.put(DBConstant.SLAVE1, sqlSessionFactorySlave1);
        sqlSessionFactoryMap.put(DBConstant.SLAVE2, sqlSessionFactorySlave2);

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
}
