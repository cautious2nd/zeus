package org.scaffold.mybatis.mongo.config;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.scaffold.mybatis.generator.util.string.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author ：zjd
 * @date ：Created By 2022/5/13 9:39
 * @description：${description}
 * @modified By：
 * @version: $version$
 */
@Configuration
@ConfigurationProperties(prefix = "mongodb.dbconfig")
@EnableConfigurationProperties(MongoOptionProperties.class)
public class MongoPlusAutoConfiguration {
    private ApplicationContext applicationContext;

    /**
     * 数据库
     */
    private String database;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 连接地址（IP:端口）
     */
    private ArrayList<String> addresses;

    /**
     * 重写
     */
    private Boolean retryWrites = true;

    /**
     * 连接配置
     */
    @Autowired
    private MongoOptionProperties mongoOptionProperties;


    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<String> getAddresses() {
        return addresses;
    }

    public void setAddresses(ArrayList<String> addresses) {
        this.addresses = addresses;
    }

    public MongoOptionProperties getMongoOptionProperties() {
        return mongoOptionProperties;
    }

    public void setMongoOptionProperties(MongoOptionProperties mongoOptionProperties) {
        this.mongoOptionProperties = mongoOptionProperties;
    }

    public Boolean getRetryWrites() {
        return retryWrites;
    }

    public void setRetryWrites(Boolean retryWrites) {
        this.retryWrites = retryWrites;
    }

    /**
     * mongo工厂
     *
     * @return org.springframework.data.mongodb.MongoDbFactory
     */
    @Bean
    public MongoClient mongoClient() {
        List<ServerAddress> serverAddressArrayList = new ArrayList<>();
        for (String address : addresses) {
            String[] hostAndPort = address.split(":");
            String host = hostAndPort[0];
            Integer port = Integer.parseInt(hostAndPort[1]);
            ServerAddress serverAddress = new ServerAddress(host, port);
            serverAddressArrayList.add(serverAddress);
        }

        MongoClientSettings.Builder mongoClientSettingBuilder =
                MongoClientSettings.builder();
        mongoClientSettingBuilder.retryWrites(retryWrites);
        mongoClientSettingBuilder.applyToClusterSettings(builderCluster -> builderCluster.hosts(serverAddressArrayList));
        mongoClientSettingBuilder.applyToConnectionPoolSettings(builder -> {
            builder.maxSize(mongoOptionProperties.getMaxConnectionPerHost());
            builder.minSize(mongoOptionProperties.getMinConnectionPerHost());
            builder.maxWaitTime(mongoOptionProperties.getMaxWaitTime(), TimeUnit.MILLISECONDS);
            builder.maxConnectionIdleTime(mongoOptionProperties.getMaxConnectionIdleTime(), TimeUnit.MILLISECONDS);
            builder.maxConnectionLifeTime(mongoOptionProperties.getMaxConnectionLifeTime(), TimeUnit.MILLISECONDS);
        });
        mongoClientSettingBuilder.applyToClusterSettings(builder -> {
            builder.serverSelectionTimeout(mongoOptionProperties.getServerSelectionTimeout(), TimeUnit.MILLISECONDS);
            builder.localThreshold(mongoOptionProperties.getLocalThreshold(), TimeUnit.MILLISECONDS);
        });
        mongoClientSettingBuilder.applyToServerSettings(builder -> {
            builder.heartbeatFrequency(mongoOptionProperties.getHeartbeatFrequency(), TimeUnit.MILLISECONDS);
            builder.minHeartbeatFrequency(mongoOptionProperties.getHeartbeatFrequency(), TimeUnit.MILLISECONDS);

        });
        mongoClientSettingBuilder.applyToSocketSettings(builder -> {
            builder.connectTimeout(mongoOptionProperties.getConnectTimeout(), TimeUnit.MILLISECONDS);
        });


        if (!(StringUtils.isEmpty(username) || StringUtils.isEmpty(password))) {
            mongoClientSettingBuilder.credential(MongoCredential.createScramSha1Credential(username, database, password.toCharArray()));
        }
        return MongoClients.create(mongoClientSettingBuilder.build());

    }

    @Bean
    public SimpleMongoClientDatabaseFactory mongoClientDatabaseFactory(MongoClient mongoClient) {
        return new SimpleMongoClientDatabaseFactory(mongoClient, database);
    }

    @Bean
    public MongoTemplate mongoTemplate(SimpleMongoClientDatabaseFactory mongoClientDatabaseFactory) {
        MongoTemplate mongoTemplate = new MongoTemplate(mongoClientDatabaseFactory);
        MongoConverter converter = mongoTemplate.getConverter();
        String typeMapper = "_class";
        if (converter.getTypeMapper().isTypeKey(typeMapper)) {
            ((MappingMongoConverter) converter).setTypeMapper(new DefaultMongoTypeMapper(null));
        }
        return mongoTemplate;
    }

//    @Bean
//    PlatformTransactionManager mongoTransactionManager(MongoDatabaseFactory mongoDatabaseFactory) {
//        return new MongoTransactionManager(mongoDatabaseFactory);
//    }
}
