package org.qiuer.ast.expression.literal;

import org.qiuer.core.Context;
import org.qiuer.exception.IException;

public class NullLiteral extends Literal {
  public String type = "NullLiteral";

  @Override
  public void compile() throws IException {
  }

  @Override
  public Object run(Context context) throws IException {
    return null;
  }

  @Override
  public Object getValue() {
    return null;
  }
}
