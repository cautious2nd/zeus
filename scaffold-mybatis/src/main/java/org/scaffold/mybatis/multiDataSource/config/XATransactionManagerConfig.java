package org.scaffold.mybatis.multiDataSource.config;

import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;

/**
 * @author : heibaiying
 * @description : JTA事务配置
 */
@EnableTransactionManagement
public class XATransactionManagerConfig {

    @Bean
    public UserTransaction userTransaction() throws Throwable {
        UserTransactionImp userTransactionImp = new UserTransactionImp();
        userTransactionImp.setTransactionTimeout(600000);
        return userTransactionImp;
    }

    @Bean(initMethod = "init", destroyMethod = "close")
    public TransactionManager atomikosTransactionManager() throws Throwable{
        UserTransactionManager userTransactionManager = new UserTransactionManager();
        userTransactionManager.setForceShutdown(false);
        userTransactionManager.setTransactionTimeout(600000);
        return userTransactionManager;
    }

    @Bean
    @Primary
    public PlatformTransactionManager transactionManager(UserTransaction userTransaction,
                                                         TransactionManager transactionManager) {
        return new JtaTransactionManager(userTransaction, transactionManager);
    }
}

