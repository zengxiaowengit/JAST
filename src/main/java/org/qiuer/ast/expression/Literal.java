package org.qiuer.ast.expression;

import org.qiuer.core.Context;
import org.qiuer.exception.IException;

import java.util.List;

public class Literal extends AbstractAssignPathExpression{
  public String type = "Literal";
  public Object value; //string | boolean | null | number | RegExp(暂不考虑支持吧。)

  public Literal(){}

  public Literal(Object o){ value = o; }

  @Override
  public void compile() throws IException {

  }

  @Override
  public Object run(Context context) throws IException {
    return value;
  }

  @Override
  public void addMemberPath(List<Object> path) {
    path.add(value);
  }
}
