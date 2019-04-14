package org.qiuer.ast.expression.literal;

import org.qiuer.ast.expression.AbstractAssignPathExpression;
import org.qiuer.core.Context;
import org.qiuer.exception.IException;

import java.util.List;

public abstract class Literal extends AbstractAssignPathExpression {
  public String type = "Literal";
}
