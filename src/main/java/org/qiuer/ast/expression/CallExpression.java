package org.qiuer.ast.expression;

import org.qiuer.ast.Property;
import org.qiuer.core.Context;
import org.qiuer.exception.IException;

import java.util.List;

public class CallExpression implements Expression{
  String type = "CallExpression";
  Expression callee;
  List<Expression> arguments;
  @Override
  public Object run(Context context) throws IException {
    return null;
  }

}
