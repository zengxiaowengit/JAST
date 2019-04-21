package org.qiuer.ast.statement;

import org.qiuer.ast.INode;
import org.qiuer.ast.expression.IExpression;
import org.qiuer.core.Context;
import org.qiuer.exception.EValidate;
import org.qiuer.exception.IException;

public class ForStatement extends Statement {
  public String type = "ForStatement";
  public INode init; // VariableDeclaration | Expression | null
  public IExpression test;
  public IExpression update;
  public IStatement body;
  @Override
  public void compile() throws IException {
    EValidate.notNull(test);
    EValidate.notNull(update);
    if(init == null) init = new EmptyStatement();
    if(body == null) body = new EmptyStatement();
  }

  @Override
  public Object run(Context context) throws IException {
    for (init.run(context); EValidate.cast(test.run(context), Boolean.class); update.run(context)){
      body.run(context);
    }
    return null;
  }
}
