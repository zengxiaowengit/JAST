package org.qiuer.ast.expression.literal;

import org.qiuer.core.Context;
import org.qiuer.exception.Const;
import org.qiuer.exception.ERuntime;
import org.qiuer.exception.EValidate;
import org.qiuer.exception.IException;

import java.util.List;

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
  public void addMemberPath(List<Object> path) throws IException {
    throw new ERuntime(Const.EXCEPTION.UNSUPPORTED_OPERATION, type + "不支持访问");
  }
}
