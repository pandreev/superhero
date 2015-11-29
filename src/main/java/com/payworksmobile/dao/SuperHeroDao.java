package com.payworksmobile.dao;


import com.payworksmobile.model.SuperHero;

import java.util.List;

public interface SuperHeroDao {

    void saveOrUpdate(SuperHero superHero);
    List<SuperHero> getAll();
    SuperHero findByName(String name);

}