package org.qiuer.ast.statement;

import org.qiuer.core.Context;
import org.qiuer.exception.IException;

public class EmptyStatement extends Statement {
  public String type = "EmptyStatement";

  @Override
  public void compile() throws IException {
  }

  @Override
  public Object run(Context context) throws IException {
    return null;
  }
}
