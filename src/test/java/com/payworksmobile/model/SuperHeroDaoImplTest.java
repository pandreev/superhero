package com.payworksmobile.model;

import static org.junit.Assert.*;

import com.payworksmobile.Application;
import com.payworksmobile.dao.SuperHeroDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class SuperHeroDaoImplTest {
    @Autowired
    private SuperHeroDao userDao;

    @Test
    public void testCreateSuperHero() {
        userDao.saveOrUpdate(createSuperHero("Robin"));

        final SuperHero robin = userDao.findByName("Robin");
        assertNotNull(robin);

        assertEquals(robin.getName(), "Robin");
        assertEquals(robin.getPublisher(), "Marvel");
        assertEquals(robin.getSkills().size(), 1);
    }

    @Test
    public void testGetAllSuperHeroes() {
        userDao.saveOrUpdate(createSuperHero("Robin", "Batman", "Catwoman"));

        final List<SuperHero> heroes = userDao.getAll();
        assertEquals(heroes.size(), 3);
    }

    private SuperHero createSuperHero(final String name, final String... ally) {
        List<SuperHero> allies = new ArrayList<>();
        if (ally.length != 0) {
            for (final String each : ally) {
                allies.add(createSuperHero(each));
            }
        }
        List<String> skills = new ArrayList<>();
        skills.add("Fly");
        return new SuperHero(name, "The Man", "Marvel", "1999-12-23", allies, skills);
    }
}