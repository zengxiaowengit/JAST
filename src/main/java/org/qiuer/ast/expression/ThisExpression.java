package org.qiuer.ast.expression;

import org.qiuer.core.Context;
import org.qiuer.exception.IException;

public class ThisExpression implements Expression{
  String type = "ThisExpression";

  @Override
  public Object run(Context context) throws IException {
    return null;
  }
}
