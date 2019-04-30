package org.qiuer.ast.expression.function;

import org.qiuer.ast.pattern.IPattern;
import org.qiuer.core.Context;
import org.qiuer.exception.EValidate;
import org.qiuer.exception.IException;

public class FunctionExpression extends Function{
  public String type = "FunctionExpression";

  @Override
  public void compile() throws IException {
    EValidate.notNull(params);
    EValidate.notNull(body);
  }

  @Override
  public Object run(Context context) throws IException {
    context.enterBlock();
    try {
      for (IPattern param : this.params) {

      }
      return this.body.run(context);
    }catch (IException e){
      throw e;
    }catch (Exception e){

    }finally {
      context.exitBlock();
    }
    return null;
  }
}
