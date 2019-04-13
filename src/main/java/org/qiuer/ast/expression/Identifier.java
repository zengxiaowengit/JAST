package org.qiuer.ast.expression;

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
    // TODO 这里不只有可能是变量值，还有可能是代码定义的函数。
    return context.getVariableValue(name);
  }

  @Override
  public void addMemberPath(List<Object> path) {
    path.add(name);
  }
}