package com.test.searchautocomplete.controller.mapper;

import com.test.searchautocomplete.controller.model.CacheData;
import com.test.searchautocomplete.models.QueryDataSet;
import java.util.HashMap;
import java.util.HashSet;
import org.springframework.stereotype.Component;

@Component
public class CacheDateToQueryDataSetMapper {

  public QueryDataSet toQueryDataSet(CacheData cacheData) {
    if (cacheData == null) {
      return new QueryDataSet(new HashSet<>(), new HashMap<>());
    }
    return QueryDataSet.builder()
        .queries(cacheData.getQueries())
        .additionalAttributes(cacheData.getAdditionalAttributes())
        .build();
  }
}
