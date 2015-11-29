package com.payworksmobile.dao.impl;

import com.payworksmobile.dao.SuperHeroDao;
import com.payworksmobile.model.SuperHero;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository("superHeroDao")
public class SuperHeroDaoImpl implements SuperHeroDao {

    @Autowired
    private SessionFactory sessionFactory;

    public SuperHeroDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
    public void saveOrUpdate(SuperHero superHero) {
        sessionFactory.getCurrentSession().saveOrUpdate(superHero);
    }

    @Override
    @Transactional
    public List<SuperHero> getAll() {
        return sessionFactory.getCurrentSession()
                .createCriteria(SuperHero.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();

    }

    @Override
    public SuperHero findByName(String name) {
        return sessionFactory.getCurrentSession().get(SuperHero.class, name);
    }
}