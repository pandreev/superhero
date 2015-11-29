package com.payworksmobile.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ElementCollection;
import javax.persistence.FetchType;
import javax.persistence.CollectionTable;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.CascadeType;
import javax.validation.constraints.Pattern;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class SuperHero {

    @Id
    @Column(name = "name")
    private String name;

    @Column(name = "pseudonym")
    private String pseudonym;

    @Column(name = "publisher")
    private String publisher;

    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    @CollectionTable(name = "HERO_SKILLS", joinColumns = @JoinColumn(name = "hero_name"))
    @Column(name = "skill")
    private List<String> skills;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    @JoinTable(name = "HERO_ALLIES",
            joinColumns = {@JoinColumn(name = "hero_name")},
            inverseJoinColumns = {@JoinColumn(name = "ally_name")})
    private List<SuperHero> allies;

    @Column(name = "firstAppearance")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}")
    private String firstAppearance;

    @Deprecated //hibernate
    public SuperHero() {
    }

    public SuperHero(String name, String pseudonym, String publisher, String firstAppearance, List<SuperHero> allies, List<String> skills) {
        this.name = name;
        this.pseudonym = pseudonym;
        this.publisher = publisher;
        this.skills = skills;
        this.allies = allies;
        this.firstAppearance = firstAppearance;
    }

    public String getName() {
        return name;
    }

    public String getPseudonym() {
        return pseudonym;
    }

    public String getPublisher() {
        return publisher;
    }


    public List<String> getSkills() {
        return Collections.unmodifiableList(skills);
    }

    public List<String> getAllies() {
        return allies.stream().map(SuperHero::getName).collect(Collectors.toList());
    }

    public String getFirstAppearance() {
        return firstAppearance;
    }
}