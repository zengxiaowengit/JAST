package org.qiuer.core;

import org.qiuer.ast.expression.function.Function;
import org.qiuer.exception.Const;
import org.qiuer.exception.ERuntime;
import org.qiuer.exception.IException;

import java.util.Map;

/**
 * 可用于运行时的block上下文需要隔离的变量保存。
 */
public class Context{
  private VariableContext variableContext = new VariableContext();
  private FunctionContext functionContext = new FunctionContext();
  private AssignContext assignContext = new AssignContext();



  public Object updateVariable(String key, Object value) throws ERuntime {
    if(assignContext.isModifiable(key)){
      return variableContext.update(key, value);
    }else
      throw new ERuntime(Const.EXCEPTION.VARIABLE_CANNOT_MODIFIED, "变量不可修改:" + key);
  }

  public void declareVariable(String key, Object value, String kind) {
    variableContext.update(key, value);
  }

  public Object getVariableValue(String key) {
    return variableContext.get(key);
  }

  public Function getFunction(Class clazz, String name) throws IException {
    return functionContext.getFunction(clazz, name);
  }

  public Function getFunction(String name) {
    return functionContext.getFunction(name);
  }

  public void declareFunction(String key, Function value) {
    functionContext.create(key, value);
  }

  public void enterBlock() {
    assignContext.enterBlock();
    variableContext.enterBlock();
    functionContext.enterBlock();
  }

  public void exitBlock() {
    assignContext.exitBlock();
    variableContext.exitBlock();
    functionContext.exitBlock();
  }

  public Map<String, Object> getCurrentContext() {
    return variableContext.getCurrentContext();
  }
}
