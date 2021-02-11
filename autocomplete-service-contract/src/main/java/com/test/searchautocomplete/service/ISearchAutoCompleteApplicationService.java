package com.test.searchautocomplete.service;

import com.test.searchautocomplete.models.QueryDataSet;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public interface ISearchAutoCompleteApplicationService {

  void cache(QueryDataSet queryDataSet);

  Set<String> getRecommendations(String query);
}
