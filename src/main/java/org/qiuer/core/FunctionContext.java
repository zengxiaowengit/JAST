package org.qiuer.core;

import org.qiuer.ast.expression.function.Function;
import org.qiuer.ast.expression.function.array.ArrayPushFunction;
import org.qiuer.exception.Const;
import org.qiuer.exception.ERuntime;
import org.qiuer.exception.IException;

import java.util.HashMap;
import java.util.List;

/**
 * 用来存放函数。
 * 主要两类：
 * 代码里自定义函数和框架内置函数。
 */
public class FunctionContext extends AbstractRuntimeContext<String, Function> {

  private static final HashMap<String, Class<? extends Function>> funcOfListRegister = new HashMap<String, Class<? extends Function>>() {
    {
      put(ArrayPushFunction.getName(), ArrayPushFunction.class);
    }
  };

  public Function getFunction(Class clazz, String name) throws IException {
    try {
      if (List.class.isAssignableFrom(clazz)){
        Function function = funcOfListRegister.get(name).newInstance();
        function.compile();
        return function;
      }else{
        throw new ERuntime(Const.EXCEPTION.UNSUPPORTED_OPERATION,"框架暂不支持的函数：" + name);
      }
    }catch (IException e1){
      throw e1;
    }
    catch (Exception e) {
      e.printStackTrace();
      throw new ERuntime(1, "框架暂不支持的函数：" + name);
    }
  }

}
