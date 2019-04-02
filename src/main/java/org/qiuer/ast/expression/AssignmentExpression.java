package org.qiuer.ast.expression;

import org.qiuer.ast.pattern.Pattern;

public class AssignmentExpression implements Expression{
  String type = "AssignmentExpression";
  //AssignmentOperator TODO 优化成enum
  String operator;
  Pattern left;
  Expression right;
}
