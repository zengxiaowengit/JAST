package org.qiuer.ast;

import org.qiuer.ast.expression.Expression;
import org.qiuer.ast.pattern.Pattern;

public class Identifier implements Pattern, Expression {
  String type = "Identifier";
  String name;
}