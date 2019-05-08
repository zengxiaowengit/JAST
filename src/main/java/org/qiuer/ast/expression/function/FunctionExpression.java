package org.qiuer.ast.expression.function;

import org.qiuer.ast.statement.BlockStatement;
import org.qiuer.core.Context;
import org.qiuer.exception.Const;
import org.qiuer.exception.ERuntime;
import org.qiuer.exception.EValidate;
import org.qiuer.exception.IException;

public class FunctionExpression extends Function{
  public String type = "FunctionExpression";

  @Override
  public void compile() throws IException {
    EValidate.notNull(params);
    EValidate.notNull(body);
    if(body instanceof BlockStatement){ //使用函数的scope。
      ((BlockStatement) body).needScope = false;
    }
  }

  @Override
  public Object run(Context context) throws IException {
    context.enterScope();
    try {
      return this.body.run(context);
    }catch (IException e){
      throw e;
    }catch (Exception e){
      e.printStackTrace();
      throw new ERuntime(Const.EXCEPTION.UNKNOWN_ERROR, e.getMessage());
    }finally {
      context.exitScope();
    }
  }
}
