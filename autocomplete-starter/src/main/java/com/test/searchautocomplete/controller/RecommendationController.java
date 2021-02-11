package com.test.searchautocomplete.controller;

import com.test.searchautocomplete.controller.mapper.CacheDateToQueryDataSetMapper;
import com.test.searchautocomplete.controller.model.CacheData;
import com.test.searchautocomplete.service.ISearchAutoCompleteApplicationService;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/autocomplete")
public class RecommendationController {

  @Autowired
  private ISearchAutoCompleteApplicationService searchAutoCompleteApplicationService;

  @Autowired
  private CacheDateToQueryDataSetMapper cacheDateToQueryDataSetMapper;

  @PostMapping(value = "/populate-queries", consumes = "application/json")
  public ResponseEntity<Void> populateQueries(
      @RequestBody CacheData cacheData) {
    searchAutoCompleteApplicationService
          .cache(cacheDateToQueryDataSetMapper.toQueryDataSet(cacheData));
    return ResponseEntity.ok().build();
  }

  @GetMapping(path = "recommendations/{query}")
  public ResponseEntity<Set<String>> getRecommendations(@PathVariable String query) {
    return ResponseEntity.ok(searchAutoCompleteApplicationService.getRecommendations(query));
  }
}
