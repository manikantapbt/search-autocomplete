package com.test.searchautocomplete.persistence;

import java.util.Set;

public interface ICache {

  void saveAll(Set<String> queryString);

  Set<String> get(String query);
}
