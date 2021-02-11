package com.test.searchautocomplete.models;

import java.util.Map;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QueryDataSet {

  private Set<String> queries;
  private Map<String, Object> additionalAttributes;
}
