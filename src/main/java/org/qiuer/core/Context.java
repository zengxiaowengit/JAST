package org.qiuer.core;

import java.util.*;

public class Context {
//  block模式的运行环境下，需要把各自的context区分开。
  private List<HashMap<String, Object>> context = new ArrayList<>();

  private HashMap<String, Object> currentContext = new HashMap<>();

  public Object get(String key){
    return currentContext.get(key);
  }

  // 这里返回的map修改值是不生效的。修改值要从update接口。
  public Map<String, Object> getCurrentContext(){
    return Collections.unmodifiableMap(currentContext);
  }

  // 更新当前context的同时，查找这个变量在哪定义。
  // 找到作用域最小的那个更新。未找到，放在当前作用域。
  public Object update(String key, Object value) {
    for (int i = context.size() - 1; i >= 0; i--){
      Map<String, Object> map = context.get(i);
      if(map.containsKey(key)){
        return map.put(key, value);
      }
    }
    return context.get(context.size() - 1).put(key, value);
  }

  public void declare(String key, Object value, String kind){
    //TODO Const 类型的声明不允许修改。
    update(key, value);
  }

  public void enterBlock(){
    currentContext = new HashMap<>();
    for (HashMap<String, Object> map : context) {
      currentContext.putAll(map);
    }
    context.add(new HashMap<>());
  }

  public void exitBlock(){
    context.remove(context.size() - 1);
  }

}
