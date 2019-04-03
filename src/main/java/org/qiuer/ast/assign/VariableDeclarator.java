/**
 * Copyright 2019 bejson.com
 */
package org.qiuer.ast.assign;

import org.qiuer.ast.Identifier;
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
      if(id instanceof Identifier){
        context.update(((Identifier)id).name, init.run(context));
      }else {
        throw new ERuntime(Const.EXCEPTION.FRAME.UN_SUPPORTED_EXPRESSION, "不支持的表达式：" + type);
      }
      return null;
    }
}