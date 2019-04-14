package org.qiuer.ast.expression.literal;

import org.qiuer.core.Context;
import org.qiuer.exception.EValidate;
import org.qiuer.exception.IException;

import java.util.List;

public class StringLiteral extends Literal {
  public String type = "StringLiteral";
  public String value;

  @Override
  public void compile() throws IException {
    EValidate.notNull(value);
  }

  @Override
  public Object run(Context context) throws IException {
    return value;
  }

  @Override
  public void addMemberPath(List<Object> path) throws IException {
    path.add(value);
  }
}
