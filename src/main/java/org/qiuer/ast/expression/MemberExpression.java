package org.qiuer.ast.expression;

import org.qiuer.core.Context;
import org.qiuer.exception.IException;

public class MemberExpression implements Expression{
  String type = "MemberExpression";
  Expression object;
  // Identifier | Expression
  Expression property;
  boolean computed;

  @Override
  public Object run(Context context) throws IException {
    return null;
  }
}
