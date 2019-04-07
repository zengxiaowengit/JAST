package org.qiuer.ast.expression;

import org.qiuer.ast.pattern.Pattern;
import org.qiuer.core.Context;
import org.qiuer.exception.IException;

import java.util.List;

public class Identifier extends AbstractAssignPathExpression implements Pattern, Expression {
  String type = "Identifier";
  public String name;

  @Override
  public Object run(Context context) throws IException {
    return null;
  }

  @Override
  public void addMemberPath(List<Object> path) {
    path.add(name);
  }
}