package com.payworksmobile.controller;

import static org.junit.Assert.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.payworksmobile.Application;
import com.payworksmobile.model.SuperHero;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest

public class SuperHeroesRestControllerTest {

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private RestTemplate restTemplate = new TestRestTemplate("payworks", "mobile");

    @Test
    public void testCreateSuperHero() throws JsonProcessingException {
        Map<String, Object> apiResponse =
                restTemplate.postForObject("http://localhost:8080/create", createHeroEntity("Robin"), Map.class, Collections.EMPTY_MAP);

        assertNotNull(apiResponse);

        String message = apiResponse.get("message").toString();
        assertEquals("SuperHero was created successfully", message);
    }

    @Test
    public void testCreateSuperHeroError() throws JsonProcessingException {
        Map<String, Object> apiResponse =
                restTemplate.postForObject("http://localhost:8080/create", createHeroEntity("Robin", "Peter"), Map.class, Collections.EMPTY_MAP);

        assertNotNull(apiResponse);

        String message = apiResponse.get("message").toString();
        assertEquals("Ally Peter do not exist.", message);
    }

    @Test
    public void testGetSuperHero() throws JsonProcessingException {
        restTemplate.postForObject("http://localhost:8080/create", createHeroEntity("Robin"), Map.class, Collections.EMPTY_MAP);
        final SuperHero superHero = restTemplate.getForObject("http://localhost:8080/hero?name=Robin", SuperHero.class);

        assertNotNull(superHero);

        assertEquals(superHero.getName(), "Robin");
        assertEquals(superHero.getPublisher(), "Marvin");
        assertEquals(superHero.getSkills().size(), 1);
    }

    @Test
    public void testGetAllSuperHeroes() throws JsonProcessingException {
        restTemplate.postForObject("http://localhost:8080/create", createHeroEntity("Robin"), Map.class, Collections.EMPTY_MAP);
        restTemplate.postForObject("http://localhost:8080/create", createHeroEntity("Batman"), Map.class, Collections.EMPTY_MAP);
        final List<SuperHero> superHeroes = restTemplate.getForObject("http://localhost:8080/all", List.class);

        assertEquals(superHeroes.size(), 2);
    }

    private HttpEntity<String> createHeroEntity(final String name, final String... allies) throws JsonProcessingException {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("name", name);
        requestBody.put("pseudonym", "The Green");
        requestBody.put("publisher", "Marvin");
        requestBody.put("firstAppearance", "1980-12-22");
        final List<String> skills = new ArrayList<>();
        skills.add("good");
        requestBody.put("skills", skills);
        final List<String> alliesList = new ArrayList<>();
        Collections.addAll(alliesList, allies);
        if(alliesList.size() > 0){
            requestBody.put("allies", alliesList);
        }
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(OBJECT_MAPPER.writeValueAsString(requestBody), requestHeaders);

    }
}
