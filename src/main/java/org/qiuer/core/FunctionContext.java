package org.qiuer.core;

import org.qiuer.ast.expression.function.Function;
import org.qiuer.ast.expression.function.SystemFunction;
import org.qiuer.exception.ERuntime;
import org.qiuer.exception.IException;
import org.reflections.Reflections;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 用来存放函数。
 * 主要两类：
 * 代码里自定义函数和框架内置函数。
 */
public class FunctionContext extends AbstractRuntimeContext<String, Function> {

  private static final HashMap<String, SystemFunction> listRegister = new HashMap<>();
  private static final HashMap<String, SystemFunction> mapRegister = new HashMap<>();
  private static final HashMap<String, SystemFunction> objectRegister = new HashMap<>();//成员函数访问.如：xxx.toString()
  private static final HashMap<String, SystemFunction> commonRegister = new HashMap<>();//非成员函数访问。如：Math.round(xxx, 2)

  static {
    try {
      Reflections reflections = new Reflections(SystemFunction.class.getPackage().getName());//不写包名也可以，但会扫描所有的包，初次加载很慢。
      Set<Class<? extends SystemFunction>> registerClasses = reflections.getSubTypesOf(SystemFunction.class);
      for (Class<? extends SystemFunction> clazz : registerClasses) {
        register(clazz);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 目前只有通过成员函数调用的方式会使用。如：list.push(1000)
   * @param clazz
   * @param name
   * @return
   * @throws IException
   */
  public SystemFunction getFunction(Class clazz, String name) throws IException {
    try {
      if (List.class.isAssignableFrom(clazz)){
        return listRegister.get(name);
      }else if (Map.class.isAssignableFrom(clazz)){
        return mapRegister.get(name);
      }else{
        return objectRegister.get(name);
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw new ERuntime(1, "框架暂不支持的函数：" + name);
    }
  }

  /**
   * 有可能是用户自定义的函数。也也可能是通用类型的函数，通过非成员函数的形式访问的。
   * @param name
   * @return
   */
  public Function getFunction(String name){
    Function function = super.get(name);
    if (function != null){
      return function;
    }
    function = commonRegister.get(name);
    return function;
  }

  private static void register(Class<? extends SystemFunction> clazz) throws IllegalAccessException, InstantiationException, IException {
    SystemFunction function = clazz.newInstance();
    function.compile();
    Class register = function.registerToOrNull();
    SystemFunction registered;
    if(register == null){
      registered = commonRegister.put(function.id.name, function);
    }else {
      if(List.class.isAssignableFrom(register)){
        registered = listRegister.put(function.id.name, function);
      }else if(Map.class.isAssignableFrom(register)){
        registered = mapRegister.put(function.id.name, function);
      }else {
        registered = objectRegister.put(function.id.name, function);
      }
    }
    if(registered != null){
      System.out.println("[Warning]: 函数注册出现重名："+ registered.id.name);
    }
  }

}

