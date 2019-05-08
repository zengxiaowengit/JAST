package org.qiuer.core;

import java.util.*;

public abstract class AbstractRuntimeContext<String, V> implements IRuntimeContext<String, V> {

  protected List<HashMap<String, V>> scope = new LinkedList<>();

  @Override
  public void create(String key, V value) {
    scope.get(scope.size() - 1).put(key, value);
  }

  @Override
  public Object update(String key, V value) {
    int size = scope.size();
    for (int i = size - 1; i >= 0; i--){
      Map<String, V> map = scope.get(i);
      if(map.containsKey(key)){
        return map.put(key, value);
      }
    }
    return scope.get(size - 1).put(key, value);
  }

  @Override
  public V get(String key) {
    for (int i = scope.size() - 1; i >= 0; i--){
      Map<String, V> map = scope.get(i);
      if(map.containsKey(key)){
        return map.get(key);
      }
    }
    return null;
  }

  @Override
  public void enterScope() {
    scope.add(new HashMap<>());
  }

  @Override
  public void exitScope() {
    scope.remove(scope.size() - 1);
  }

  // 这里返回的map修改值是不生效的。修改值要从update接口。
  @Override
  public Map<String, V> getCurrentContext(){
    HashMap<String, Object>  currentContext = new HashMap<>();
    for (HashMap<String, V> map : scope) {
      currentContext.putAll(map);
    }
    return (Map<String, V>) currentContext;
  }
}
