package org.qiuer.ast.expression.literal;

import org.qiuer.core.Context;
import org.qiuer.exception.Const;
import org.qiuer.exception.ERuntime;
import org.qiuer.exception.EValidate;
import org.qiuer.exception.IException;

import java.util.List;

public class BooleanLiteral extends Literal {
  public String type = "StringLiteral";
  public boolean value;

  @Override
  public void compile() throws IException {
    EValidate.notNull(value);
  }

  @Override
  public Object run(Context context) throws IException {
    return value;
  }

  @Override
  public void addMemberPath(List<String> path) throws IException {
    throw new ERuntime(Const.EXCEPTION.UNSUPPORTED_OPERATION, type + "不支持访问");
  }
}
