package org.qiuer.ast.expression.function;

import org.qiuer.core.Context;
import org.qiuer.exception.EValidate;
import org.qiuer.exception.IException;

/**
 * 在js代码里，使用者自己写的通过function aaa() { ... } 定义的函数。
 */
public class CustomFunction extends Function {
  public String type = "CustomFunction";

  @Override
  public void compile() throws IException {
    EValidate.notNull(id);
    EValidate.notNull(body);
  }

  @Override
  public Object run(Context context) throws IException {
    return this.body.run(context);
  }
}