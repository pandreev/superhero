package com.payworksmobile.controller;


import com.payworksmobile.model.SuperHero;
import com.payworksmobile.dao.SuperHeroDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class SuperHeroesRestController {

    @Autowired
    private SuperHeroDao userDao;

    @RequestMapping("/all")
    public List<SuperHero> all() {
        return userDao.getAll();
    }

    @RequestMapping("/hero")
    public SuperHero hero(@RequestParam(value = "name", required = true) String name) {
        return userDao.findByName(name);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Map<String, String> createHero(@RequestBody Map<String, Object> hero) {
        final String name = hero.get("name").toString();
        final String pseudonym = hero.get("pseudonym").toString();
        final String publisher = hero.get("publisher").toString();
        final String firstAppearance = hero.get("firstAppearance").toString();
        if (!firstAppearance.matches("\\d{4}-\\d{2}-\\d{2}")) {
            throw new RuntimeException("firstAppearance " + firstAppearance + " needs to be in format YYYY-MM-DD.");
        }
        final List<String> skills = new ArrayList<>();
        final Object skillsObject = hero.get("skills");
        if (skillsObject instanceof List) {
            skills.addAll((List<String>) skillsObject);
        } else {
            throw new RuntimeException("skills " + skillsObject + " needs to be list.");
        }

        final List<SuperHero> allies = new ArrayList<>();
        Object alliesObject = hero.get("allies");
        if (alliesObject != null) {
            if (alliesObject instanceof List) {
                final List<String> alliesString = (List<String>) alliesObject;
                for (final String allyName : alliesString) {
                    SuperHero ally = userDao.findByName(allyName);
                    if (ally == null) {
                        throw new RuntimeException("Ally " + allyName + " do not exist.");
                    }
                    allies.add(ally);
                }
            } else {
                throw new RuntimeException("allies " + skillsObject + " needs to be list.");
            }

        }

        SuperHero superHero = new SuperHero(name, pseudonym, publisher, firstAppearance, allies, skills);
        userDao.saveOrUpdate(superHero);
        Map<String, String> response = new LinkedHashMap<>();
        response.put("message", "SuperHero was created successfully");
        return response;
    }
}