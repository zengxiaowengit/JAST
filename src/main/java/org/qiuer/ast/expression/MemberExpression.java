package org.qiuer.ast.expression;

public class MemberExpression implements Expression{
  String type = "MemberExpression";
  Expression object;
  // Identifier | Expression
  Expression property;
  boolean computed;
}
