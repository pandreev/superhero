package com.payworksmobile.config;

import javax.sql.DataSource;

import com.payworksmobile.dao.AccountDao;
import com.payworksmobile.dao.impl.AccountDaoImpl;
import com.payworksmobile.model.Account;
import com.payworksmobile.model.SuperHero;
import com.payworksmobile.dao.SuperHeroDao;
import com.payworksmobile.dao.impl.SuperHeroDaoImpl;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class RootConfig {


    @Bean(name = "dataSource")
    public DataSource getDataSource() {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        return builder
                .setType(EmbeddedDatabaseType.H2).addScript("db/create.sql").addScript("db/insert.sql")
                .build();
    }

    @Autowired
    @Bean(name = "sessionFactory")
    public SessionFactory getSessionFactory(DataSource dataSource) {
        LocalSessionFactoryBuilder sessionBuilder = new LocalSessionFactoryBuilder(dataSource);
        sessionBuilder.addAnnotatedClasses(SuperHero.class, Account.class);
        return sessionBuilder.buildSessionFactory();
    }

    @Autowired
    @Bean(name = "transactionManager")
    public HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory) {
        return new HibernateTransactionManager(sessionFactory);
    }

    @Autowired
    @Bean(name = "superHeroDao")
    public SuperHeroDao getSuperHeroDao(SessionFactory sessionFactory) {
        return new SuperHeroDaoImpl(sessionFactory);
    }

    @Autowired
    @Bean(name = "accountDao")
    public AccountDao getAccountDao(SessionFactory sessionFactory) {
        return new AccountDaoImpl(sessionFactory);
    }

}
