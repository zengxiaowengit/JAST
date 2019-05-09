package org.qiuer.ast.expression.literal;

import org.qiuer.ast.expression.Expression;

public abstract class Literal extends Expression {
  public String type = "Literal";

  public abstract Object getValue();
}
