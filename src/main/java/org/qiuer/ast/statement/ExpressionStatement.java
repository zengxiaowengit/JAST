package org.qiuer.ast.statement;


import org.qiuer.ast.expression.Expression;

public class ExpressionStatement implements Statement {
  String type = "ExpressionStatement";
  Expression expression;
}