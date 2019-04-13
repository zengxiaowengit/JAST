package org.qiuer.ast.expression;

import org.qiuer.core.Context;
import org.qiuer.exception.IException;

import java.util.List;

public class ThisExpression extends AbstractAssignPathExpression{
  String type = "ThisExpression";

  @Override
  public void compile() throws IException {

  }

  @Override
  public Object run(Context context) throws IException {
    return null;
  }

  @Override
  public void addMemberPath(List<Object> path) {
    path.add("this");
  }
}
