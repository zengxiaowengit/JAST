package org.qiuer.ast.assign;

import org.qiuer.ast.Node;
import org.qiuer.ast.expression.Expression;
import org.qiuer.ast.expression.Identifier;
import org.qiuer.ast.expression.function.Function;
import org.qiuer.ast.pattern.IPattern;
import org.qiuer.core.Context;
import org.qiuer.exception.Const;
import org.qiuer.exception.ERuntime;
import org.qiuer.exception.IException;

public class VariableDeclarator extends Node {
  String type = "VariableDeclarator";
  IPattern id;
  Expression init;

  @Override
  public void compile() throws IException {

  }

  @Override
  public Object run(Context context) throws IException {
    if (id instanceof Identifier) {
      if(init instanceof Function){
        context.declareFunction(((Identifier) id).name, (Function) init);
      }else
        context.declareVariable(((Identifier) id).name, init.run(context), "let");
    } else {
      throw new ERuntime(Const.EXCEPTION.UNSUPPORTED_EXPRESSION, "不支持的表达式：" + type);
    }
    return null;
  }
}