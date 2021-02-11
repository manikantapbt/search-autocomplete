package com.test.searchautocomplete.controller.model;

import java.util.Map;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CacheData {

  private Set<String> queries;
  private Map<String, Object> additionalAttributes;
}
