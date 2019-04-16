package org.qiuer.ast.expression.function;

import org.qiuer.core.Context;
import org.qiuer.exception.IException;

/**
 * 箭头函数其实是函数定义，不是函数调用。
 */
public class ArrowFunctionExpression extends Function{
  public String type = "ArrowFunctionExpression";

  @Override
  public Object run(Context context) throws IException {
    return this.body.run(context);
  }
}
