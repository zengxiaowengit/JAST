package org.qiuer.ast.expression.function;

import org.qiuer.ast.expression.Identifier;
import org.qiuer.core.Context;
import org.qiuer.exception.IException;

import java.util.Date;

public class SysDateFunction extends SystemFunction{

  @Override
  public Class registerToOrNull() {
    return null;
  }

  @Override
  public boolean allowPropCall() {
    return false;
  }

  @Override
  public void compile() throws IException {
    this.id = new Identifier("sysdate");
  }

  @Override
  public Object run(Context context) throws IException {
    return new Date();
  }
}
