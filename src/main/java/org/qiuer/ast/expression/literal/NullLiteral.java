package org.qiuer.ast.expression.literal;

import org.qiuer.core.Context;
import org.qiuer.exception.Const;
import org.qiuer.exception.ERuntime;
import org.qiuer.exception.EValidate;
import org.qiuer.exception.IException;

import java.util.List;

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
  public void addMemberPath(List<Object> path) throws IException {
    throw new ERuntime(Const.EXCEPTION.UNSUPPORTED_OPERATION, type + "不支持访问");
  }
}
