package com.test.searchautocomplete.functionaltests;


import com.test.searchautocomplete.controller.model.CacheData;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class RecommendationAppTest {

  @LocalServerPort
  private int port;

  @Autowired
  private RestTemplate restTemplate;

  @Test
  void test() {
    Set<String> testsample = new HashSet<>(
        Arrays.asList("baby yogert", "baby yoda", "baby yoda stuffed animal", "baby yoda toy"));
    CacheData cacheData = new CacheData(testsample, new HashMap<>());

    ResponseEntity<Void> responseEntity = populateQueries(cacheData);
    Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    String query = "baby";
    ResponseEntity<Set> recommendationsEntity = getRecommendations(query);
    Assertions.assertEquals(HttpStatus.OK, recommendationsEntity.getStatusCode());
    Assertions.assertEquals(4, recommendationsEntity.getBody().size());
  }

  @ParameterizedTest
  @ValueSource(strings = {"a", "b", "man", "baby", "bh"})
  void recommendationsThreadSafe(String query) {
    Set<String> testSample = new HashSet<>(
        Arrays.asList("baby yogert", "baby yoda stuffed animal", "baby yoda toy", "baby yoda",
            "baby yodha", "baby shoes", "bab", "manikanta", "mammal", "halls", "hello",
            "bhanu"));
    CacheData cacheData = new CacheData(testSample, new HashMap<>());
    testSample.stream().map(x -> new CacheData(Collections.singleton(x), new HashMap<>())
    ).collect(Collectors.toList()).parallelStream()
        .forEach(this::populateQueries);
    Set<String> result = getRecommendations(query).getBody();
    List<String> expectedResult = testSample.stream().filter(x -> x.startsWith(query))
        .collect(Collectors.toList());
    assert result != null;
    Assertions.assertEquals(expectedResult.size(), result.size());
    result.forEach(x -> Assertions.assertTrue(expectedResult.contains(x)));
  }

  private ResponseEntity<Void> populateQueries(CacheData cacheData) {
    return restTemplate.postForEntity("http://localhost:" + port
        + "/autocomplete/populate-queries", cacheData, Void.class);
  }

  private ResponseEntity<Set> getRecommendations(String query) {
    return restTemplate
        .getForEntity("http://localhost:" + port
            + "/autocomplete/recommendations/" + query, Set.class);
  }

  @TestConfiguration
  public static class Config {

    @Bean
    public RestTemplate restTemplate() {
      return new RestTemplate();
    }
  }
}
