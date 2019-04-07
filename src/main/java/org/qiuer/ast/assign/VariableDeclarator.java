package org.qiuer.ast.assign;

import org.qiuer.ast.expression.Identifier;
import org.qiuer.ast.Node;
import org.qiuer.ast.expression.Expression;
import org.qiuer.ast.pattern.Pattern;
import org.qiuer.core.Context;
import org.qiuer.exception.Const;
import org.qiuer.exception.ERuntime;
import org.qiuer.exception.IException;

public class VariableDeclarator implements Node {
  String type = "VariableDeclarator";
  Pattern id;
  Expression init;

  @Override
  public Object run(Context context) throws IException {
    if (id instanceof Identifier) {
      context.declare(((Identifier) id).name, init.run(context), "let");
    } else {
      throw new ERuntime(Const.EXCEPTION.FRAME.UNSUPPORTED_EXPRESSION, "不支持的表达式：" + type);
    }
    return null;
  }
}