package org.qiuer.ast;

import org.qiuer.ast.expression.Expression;
import org.qiuer.core.Context;
import org.qiuer.exception.IException;

public class Literal implements Expression {
  String type = "Literal";
  // string | boolean | null | number | RegExp
  Object value;

  @Override
  public Object run(Context context) throws IException {
    return null;
  }
}