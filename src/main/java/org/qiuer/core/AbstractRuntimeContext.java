package org.qiuer.core;

import java.util.*;

public abstract class AbstractRuntimeContext<String, V> implements IRuntimeContext<String, V> {

  protected List<HashMap<String, Object>> context = new ArrayList<>();

  @Override
  public Object update(String key, V value) {
    int size = context.size();
    for (int i = size - 1; i >= 0; i--){
      Map<String, Object> map = context.get(i);
      if(map.containsKey(key)){
        return map.put(key, value);
      }
    }
    return context.get(size - 1).put(key, value);
  }

  @Override
  public Object get(String key) {
    for (int i = context.size() - 1; i >= 0; i--){
      Map<String, Object> map = context.get(i);
      if(map.containsKey(key)){
        return map.get(key);
      }
    }
    return null;
  }

  @Override
  public void enterBlock() {
    context.add(new HashMap<>());
  }

  @Override
  public void exitBlock() {
    context.remove(context.size() - 1);
  }

  // 这里返回的map修改值是不生效的。修改值要从update接口。
  @Override
  public Map<String, V> getCurrentContext(){
    HashMap<String, Object>  currentContext = new HashMap<>();
    for (HashMap<String, Object> map : context) {
      currentContext.putAll(map);
    }
    return (Map<String, V>) Collections.unmodifiableMap(currentContext);
  }
}
