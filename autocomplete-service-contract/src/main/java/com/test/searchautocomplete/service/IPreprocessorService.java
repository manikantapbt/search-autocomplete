package com.test.searchautocomplete.service;

import com.test.searchautocomplete.models.QueryDataSet;
import java.util.Set;

/**
 * Preprocessor hook for normalizing the query strings, can also serve business/functional
 * requirements like block listing, spell checks.
 */
@FunctionalInterface
public interface IPreprocessorService {

  Set<String> process(QueryDataSet queryDataSet);
}
