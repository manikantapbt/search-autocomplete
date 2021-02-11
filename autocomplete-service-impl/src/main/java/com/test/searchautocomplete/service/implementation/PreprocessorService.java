package com.test.searchautocomplete.service.implementation;

import com.test.searchautocomplete.models.QueryDataSet;
import com.test.searchautocomplete.service.IPreprocessorService;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class PreprocessorService implements IPreprocessorService {

  @Override
  public Set<String> process(QueryDataSet queryDataSet) {
    return queryDataSet.getQueries().stream().map(String::toLowerCase).collect(Collectors.toSet());
  }
}
