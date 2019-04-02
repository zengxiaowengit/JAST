package org.qiuer.ast;

import org.qiuer.ast.expression.Expression;

public class Literal implements Expression {
  String type = "Literal";
  // string | boolean | null | number | RegExp
  Object value;
}