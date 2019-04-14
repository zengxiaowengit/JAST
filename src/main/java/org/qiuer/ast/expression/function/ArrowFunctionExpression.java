package org.qiuer.ast.expression.function;

import org.qiuer.core.Context;
import org.qiuer.exception.IException;

public class ArrowFunctionExpression extends Function{
  public String type = "ArrowFunctionExpression";

  @Override
  public Object run(Context context) throws IException {
    return this.body.run(context);
  }
}
