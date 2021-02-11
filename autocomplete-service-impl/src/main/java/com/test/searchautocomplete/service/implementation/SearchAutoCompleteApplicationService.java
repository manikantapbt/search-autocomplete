package com.test.searchautocomplete.service.implementation;

import com.test.searchautocomplete.models.QueryDataSet;
import com.test.searchautocomplete.persistence.ICache;
import com.test.searchautocomplete.service.IPostProcessorService;
import com.test.searchautocomplete.service.IPreprocessorService;
import com.test.searchautocomplete.service.ISearchAutoCompleteApplicationService;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SearchAutoCompleteApplicationService implements ISearchAutoCompleteApplicationService {

  @Autowired
  private IPostProcessorService postProcessorService;

  @Autowired
  private IPreprocessorService preprocessorService;

  @Autowired
  private ICache cache;

  public void cache(QueryDataSet queryDataSet) {
    cache.saveAll(preprocessorService.process(queryDataSet));
  }

  @Override
  public Set<String> getRecommendations(String query) {
    return postProcessorService.process(cache.get(query.toLowerCase()));
  }
}
