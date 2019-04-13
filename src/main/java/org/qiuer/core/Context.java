package org.qiuer.core;

import org.qiuer.ast.expression.function.Function;
import org.qiuer.ast.expression.function.array.ArrayPushFunction;
import org.qiuer.exception.Const;
import org.qiuer.exception.ERuntime;
import org.qiuer.exception.IException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 可用于运行时的block上下文需要隔离的变量保存。
 */
public class Context{
  private VariableContext variableContext = new VariableContext();
  private FunctionContext functionContext = new FunctionContext();
  private AssignContext assignContext = new AssignContext();
  private static final HashMap<String, Class<? extends Function>> functionRegister = new HashMap<String, Class<? extends Function>>() {
    {
      put(ArrayPushFunction.getName(), ArrayPushFunction.class);
    }
  };


  public Object update(String key, Object value) throws ERuntime {
    if(assignContext.isModifiable(key)){
      return variableContext.update(key, value);
    }else
      throw new ERuntime(Const.EXCEPTION.VARIABLE_CANNOT_MODIFIED, "变量不可修改:" + key);
  }

  public void declare(String key, Object value, String kind) {
    variableContext.update(key, value);
  }

  public Object getVariableValue(String key) {
    return variableContext.get(key);
  }

  public void enterBlock() {
    assignContext.enterBlock();
    variableContext.enterBlock();
  }

  public void exitBlock() {
    assignContext.exitBlock();
    variableContext.exitBlock();
  }

  public Function getFunction(Class clazz, String name) throws IException {
    try {
      if (List.class.isAssignableFrom(clazz)){
        Function function = functionRegister.get(name).newInstance();
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

  public Map<String, Object> getCurrentContext() {
    return variableContext.getCurrentContext();
  }
}
