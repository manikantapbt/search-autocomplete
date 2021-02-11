package com.test.searchautocomplete.persistence;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;
import org.springframework.stereotype.Component;

@Component
public class Cache implements ICache {

  private TrieNode head = new TrieNode();

  @Override
  public void saveAll(Set<String> queries) {
    queries.forEach(head::save);
  }

  @Override
  public Set<String> get(String query) {
    return new HashSet<>(head.getRecommendations(query));
  }
}


class TrieNode {

  private final Map<Character, TrieNode> children = new ConcurrentHashMap<>();
  private ReentrantLock lock = new ReentrantLock();
  private char elementValue;
  private boolean isTerminalNode = false;

  private TrieNode getNode(char c) {
    return children.get(c);
  }

  public void save(String s) {
    addElement(s, 0);
  }

  /**
   * Builds the trie, if the character is not found, the insertion is locked
   * for insertion to make it thread safe.
   *
   * @param query {{@link String}} Query string
   * @param index {{@link Integer}} index of character in query
   */
  private void addElement(String query, int index) {
    if (index == query.length()) {
      this.isTerminalNode = true;
      return;
    }
    char element = query.charAt(index);
    TrieNode nextNode = getNode(element);
    if (nextNode != null) {
      nextNode.addElement(query, index + 1);
    } else {
      lock.lock();
      TrieNode nextNodeCheck = getNode(element);
      if (nextNodeCheck == null) {
        TrieNode node = new TrieNode();
        node.elementValue = element;
        this.children.put(element, node);
      }
      lock.unlock();
      children.get(element).addElement(query, index + 1);
    }
  }

  public List<String> getRecommendations(String query) {
    List<String> result = new ArrayList<>();
    TrieNode node = search(query, 0);
    if (node != null) node.generateRecommendations(result, query);
    return result;
  }

  private void generateRecommendations(List<String> result, String query) {
    if (this.isTerminalNode) result.add(query);
    this.children.values()
        .forEach(node -> node.generateRecommendations(result, query + node.elementValue));
  }

  private TrieNode search(String query, int index) {
    if (query.length() == index) return this;
    TrieNode node = this.children.get(query.charAt(index));
    return node != null ? node.search(query, index + 1) : null;
  }
}
