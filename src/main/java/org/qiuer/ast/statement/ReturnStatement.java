package org.qiuer.ast.statement;

import org.qiuer.ast.expression.Expression;
import org.qiuer.core.Context;
import org.qiuer.exception.IException;

public class ReturnStatement extends Statement {
  public String type = "ReturnStatement";
  public Expression argument;

  @Override
  public void compile() throws IException {

  }

  @Override
  public Object run(Context context) throws IException {
    if (argument == null)
      return null;
    return argument.run(context);
  }
}
