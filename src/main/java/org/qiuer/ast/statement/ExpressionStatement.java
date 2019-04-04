package org.qiuer.ast.statement;


import org.qiuer.ast.expression.Expression;
import org.qiuer.core.Context;
import org.qiuer.exception.IException;

public class ExpressionStatement implements Statement {
  String type = "ExpressionStatement";
  Expression expression;

  @Override
  public Object run(Context context) throws IException {
    return expression.run(context);
  }
}