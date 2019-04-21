package org.qiuer.ast.statement;

import org.qiuer.ast.expression.IExpression;
import org.qiuer.core.Context;
import org.qiuer.exception.EValidate;
import org.qiuer.exception.IException;

public class IfStatement extends Statement {
  public String type = "IfStatement";
  public IExpression test;
  public IStatement consequent;
  public IStatement alternate; //else。
  @Override
  public void compile() throws IException {
    EValidate.notNull(test);
    EValidate.notNull(consequent);
    if (alternate == null) alternate = new EmptyStatement();
  }

  @Override
  public Object run(Context context) throws IException {
    if(EValidate.cast(test.run(context), Boolean.class))
      consequent.run(context);
    else
      alternate.run(context);
    return null;
  }
}
