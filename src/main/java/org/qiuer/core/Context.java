package org.qiuer.core;

import org.qiuer.ast.assign.AssignKind;
import org.qiuer.exception.Const;
import org.qiuer.exception.ERuntime;

import java.util.Map;

/**
 * 可用于运行时的block上下文需要隔离的变量保存。
 */
public class Context{
  AssignContext assignContext = new AssignContext();
  VariableContext variableContext = new VariableContext();

  public Object update(String key, Object value) throws ERuntime {
    if(assignContext.isModifiable(key)){
      return variableContext.update(key, value);
    }else
      throw new ERuntime(Const.EXCEPTION.FRAME.VARIABLE_CANNOT_MODIFIED, "变量不可修改:" + key);
  }

  public void declare(String key, Object value, String kind) {
    variableContext.update(key, value);
  }

  public Object get(String key) {
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

  public Map<String, Object> getCurrentContext() {
    return variableContext.getCurrentContext();
  }
}
