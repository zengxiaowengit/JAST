package org.qiuer.ast.expression;

import org.qiuer.ast.pattern.Pattern;
import org.qiuer.core.Context;
import org.qiuer.exception.IException;

public class AssignmentExpression implements Expression{
  String type = "AssignmentExpression";
  //AssignmentOperator TODO 优化成enum
  String operator;
  Pattern left;
  Expression right;

  @Override
  public Object run(Context context) throws IException {
    return null;
  }
}
