package com.test.searchautocomplete.service;

import java.util.Set;

/**
 * Post processor hook for recommendations, this hook can be used to filter the recommendations or
 * to add promoted searched results or to targeted business needs
 */
@FunctionalInterface
public interface IPostProcessorService {

  Set<String> process(Set<String> resultStrings);
}
