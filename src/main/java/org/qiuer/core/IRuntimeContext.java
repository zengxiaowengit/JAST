package org.qiuer.core;

import org.qiuer.exception.ERuntime;

import java.util.Map;

public interface IRuntimeContext<K, V> {

  void create(K key, V value) throws ERuntime;
  //未找到已声明的变量会在最小作用域新声明一个变量。
  Object update(K key, V value) throws ERuntime;

  Object get(K key);

  void enterBlock();

  void exitBlock();

  // 这里返回的是不可修改的map。修改值要从update接口。
  Map<K, V> getCurrentContext();
}

