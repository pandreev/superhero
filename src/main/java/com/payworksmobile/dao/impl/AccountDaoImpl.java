package com.payworksmobile.dao.impl;

import com.payworksmobile.dao.AccountDao;
import com.payworksmobile.model.Account;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository("accountDao")
public class AccountDaoImpl implements AccountDao{

    @Autowired
    private SessionFactory sessionFactory;

    public AccountDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public Account findByName(String name) {
        return sessionFactory.getCurrentSession().get(Account.class, name);
    }

}
