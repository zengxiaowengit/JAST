package org.qiuer.ast.expression.literal;

import org.qiuer.core.Context;
import org.qiuer.exception.EValidate;
import org.qiuer.exception.IException;

public class RegExpLiteral extends Literal {
  public String type = "RegExpLiteral";
  public String pattern;
  public String flags;

  @Override
  public void compile() throws IException {
    EValidate.notNull(pattern);
  }

  @Override
  public Object run(Context context) throws IException {
    return pattern;
  }

  @Override
  public Object getValue() {
    return this.pattern;
  }
}
