package org.qiuer.ast;

import org.qiuer.ast.expression.Expression;
import org.qiuer.ast.pattern.Pattern;
import org.qiuer.core.Context;
import org.qiuer.exception.IException;

public class Identifier implements Pattern, Expression {
  String type = "Identifier";
  public String name;

  @Override
  public Object run(Context context) throws IException {
    return null;
  }
}