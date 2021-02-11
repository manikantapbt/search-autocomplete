package com.test.searchautocomplete.service.implementation;

import com.test.searchautocomplete.service.IPostProcessorService;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class PostProcessorService implements IPostProcessorService {

  @Override
  public Set<String> process(Set<String> resultStrings) {
    return resultStrings;
  }
}
