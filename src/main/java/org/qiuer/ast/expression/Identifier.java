package org.qiuer.ast.expression;

import org.qiuer.core.Context;
import org.qiuer.exception.EValidate;
import org.qiuer.exception.IException;

public class Identifier extends Expression{
  String type = "Identifier";
  public String name;

  public Identifier(){}

  public Identifier(String name) {
    this.name = name;
  }

  @Override
  public void compile() throws IException {
    EValidate.notEmpty(name);
  }

  @Override
  public Object run(Context context) throws IException {
    Object ret = context.getVariableValue(name);
    if(ret == null){
      ret = context.getFunction(name);
    }
    return ret;
  }
}