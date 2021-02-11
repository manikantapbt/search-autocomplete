package com.test.searchautocomplete.controller;


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


@SpringBootTest
class RecommendationControllerTest {

  @Autowired
  private RecommendationController recommendationController;

  @Test
  void happyPath() {
    Set<String> testSample = new HashSet<>(
        Arrays.asList("baby yogert", "baby yoda", "baby yoda stuffed animal", "baby yoda toy"));
    CacheData cacheData = new CacheData(testSample, new HashMap<>());
    recommendationController.populateQueries(cacheData);
    Set<String> result = recommendationController.getRecommendations("baby").getBody();
    assert result != null;
    result.forEach(x -> Assertions.assertTrue(testSample.contains(x)));
  }

  @Test
  void noneRecommedation() {
    Set<String> testSample = new HashSet<>(
        Arrays.asList("baby yogert", "baby yoda", "baby yoda stuffed animal", "baby yoda toy"));
    CacheData cacheData = new CacheData(testSample, new HashMap<>());
    recommendationController.populateQueries(cacheData);
    Set<String> result = recommendationController.getRecommendations("x").getBody();
    assert result != null;
    Assertions.assertTrue(result.isEmpty());
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
        .forEach(x -> recommendationController.populateQueries(x));
    Set<String> result = recommendationController.getRecommendations(query).getBody();
    List<String> expectedResult = testSample.stream().filter(x -> x.startsWith(query))
        .collect(Collectors.toList());
    assert result != null;
    Assertions.assertEquals(expectedResult.size(), result.size());
    result.forEach(x -> Assertions.assertTrue(expectedResult.contains(x)));
  }
}