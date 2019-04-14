package org.qiuer.ast.statement;


import org.qiuer.ast.expression.Expression;
import org.qiuer.core.Context;
import org.qiuer.exception.EValidate;
import org.qiuer.exception.IException;

public class ExpressionStatement extends Statement {
  public String type = "ExpressionStatement";
  public Expression expression;

  @Override
  public void compile() throws IException {
    EValidate.notNull(expression);
  }

  @Override
  public Object run(Context context) throws IException {
    return expression.run(context);
  }
}