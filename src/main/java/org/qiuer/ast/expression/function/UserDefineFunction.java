package org.qiuer.ast.expression.function;

import org.qiuer.ast.statement.BlockStatement;
import org.qiuer.core.Context;
import org.qiuer.exception.EValidate;
import org.qiuer.exception.IException;

/**
 * 在js代码里，使用者自己写的通过function aaa() { ... } 定义的函数。
 */
public class UserDefineFunction extends Function {
  public String type = "UserDefineFunction";

  @Override
  public void compile() throws IException {
    EValidate.notNull(id);
    EValidate.notNull(body);
    if(body instanceof BlockStatement){
      ((BlockStatement) body).needScope = false;
    }
  }

  @Override
  public Object run(Context context) throws IException {
    return this.body.run(context);
  }
}