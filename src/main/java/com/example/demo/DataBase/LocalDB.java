package com.example.demo.DataBase;
import bitronix.tm.BitronixTransactionManager;
import bitronix.tm.TransactionManagerServices;
import bitronix.tm.resource.jdbc.PoolingDataSource;
import org.springframework.context.annotation.*;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.sql.DataSource;
import javax.transaction.TransactionManager;
import java.util.Properties;


/**
 * Класс конфигурация для подключения к базе данных и для мененджера транзакций.
 * @autor Шаля Андрей
 * @version 2.0
 */
@Profile("default")
@Configuration
@EnableTransactionManagement
public class LocalDB{


    /**
     * Инициализация бина {@link BitronixTransactionManager} для мененджера транзакций bitronix
     * @return возвращает {@link BitronixTransactionManager}
     */
    @Bean(name = "bitronixTransactionManager")
    @DependsOn
    public BitronixTransactionManager bitronixTransactionManager() throws Throwable {
        BitronixTransactionManager bitronixTransactionManager = TransactionManagerServices.getTransactionManager();
        bitronixTransactionManager.setTransactionTimeout(10000);
        CustomJtaPlatform.setUserTransaction(bitronixTransactionManager);
        CustomJtaPlatform.setTransactionManager(bitronixTransactionManager);
        return bitronixTransactionManager;
    }
    /**
     * Инициализация бина {@link PlatformTransactionManager} для мененджера транзакций bitronix.
     * Для инициализации нужен бин {@link BitronixTransactionManager}
     * @return возвращает {@link PlatformTransactionManager}
     */
    @Bean(name = "transactionManager")
    @DependsOn({"bitronixTransactionManager"})
    public PlatformTransactionManager transactionManager(TransactionManager bitronixTransactionManager) throws Throwable {
        return new JtaTransactionManager(bitronixTransactionManager);
    }

    /**
     * Инициализация бина {@link DataSource} с кредами для подключения к БД.
     * @return возвращает {@link PoolingDataSource} implemented {@link DataSource}
     */
    @Bean(name = "primaryPgDataSource")
    @Primary
    public DataSource primaryMySqlDataSource() {
        PoolingDataSource bitronixDataSourceBean = new PoolingDataSource();
        bitronixDataSourceBean.setMaxPoolSize(5);
        bitronixDataSourceBean.setUniqueName("primaryPgDataSourceResource");
        bitronixDataSourceBean.setClassName("org.postgresql.xa.PGXADataSource");
        bitronixDataSourceBean.setAllowLocalTransactions(true);
        Properties properties = new Properties();
        properties.put("user",  "postgres");
        properties.put("password",  "postgres");
        properties.put("url", "jdbc:postgresql://localhost:5432/postgres");
        bitronixDataSourceBean.setDriverProperties(properties);
        return bitronixDataSourceBean;
    }

}