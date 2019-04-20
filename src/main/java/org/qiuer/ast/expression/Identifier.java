package org.qiuer.ast.expression;

import org.qiuer.ast.expression.function.Function;
import org.qiuer.core.Context;
import org.qiuer.exception.EValidate;
import org.qiuer.exception.IException;

import java.util.List;

public class Identifier extends AbstractAssignPathExpression{
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

  @Override
  public void addMemberPath(List<String> path) {
    path.add(this.name);
  }

  public Function getFunction(Context context){
    return context.getFunction(this.name);
  }
}