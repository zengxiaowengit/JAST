package org.qiuer.core;

import org.qiuer.ast.expression.function.CustomFunction;
import org.qiuer.ast.expression.function.Function;
import org.qiuer.exception.Const;
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

  private static final HashMap<String, CustomFunction> listRegister = new HashMap<>();
  private static final HashMap<String, CustomFunction> mapRegister = new HashMap<>();
  private static final HashMap<String, CustomFunction> commonRegister = new HashMap<>();

  static {
    try {
      Reflections reflections = new Reflections(CustomFunction.class.getPackage().getName());
      Set<Class<? extends CustomFunction>> registerClasses = reflections.getSubTypesOf(CustomFunction.class);
      for (Class<? extends CustomFunction> clazz : registerClasses) {
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
  public CustomFunction getFunction(Class clazz, String name) throws IException {
    try {
      if (List.class.isAssignableFrom(clazz)){
        return listRegister.get(name);
      }else if (Map.class.isAssignableFrom(clazz)){
        return mapRegister.get(name);
      }else{
        throw new ERuntime(Const.EXCEPTION.UNSUPPORTED_OPERATION,clazz.getSimpleName() + "类型暂不支持的函数：" + name);
      }
    }catch (IException e1){
      throw e1;
    }
    catch (Exception e) {
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

  private static void register(Class<? extends CustomFunction> clazz) throws IllegalAccessException, InstantiationException, IException {
    CustomFunction function = clazz.newInstance();
    function.compile();
    Class register = function.registerTo();
    CustomFunction registered;
    if(List.class.isAssignableFrom(register)){
      registered = listRegister.put(function.getName(), function);
    }else if(Map.class.isAssignableFrom(register)){
      registered = mapRegister.put(function.getName(), function);
    }else {
      registered = commonRegister.put(function.getName(), function);
    }
    if(registered != null){
      System.out.println("[Warning]: 函数注册出现重名："+ registered.getName());
    }
  }

}
