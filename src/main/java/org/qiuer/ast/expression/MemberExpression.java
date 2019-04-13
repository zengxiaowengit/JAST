package org.qiuer.ast.expression;

import org.qiuer.core.Context;
import org.qiuer.exception.IException;

import java.util.ArrayList;
import java.util.List;

public class MemberExpression extends AbstractAssignPathExpression{
  public String type = "MemberExpression";
  public AbstractAssignPathExpression object;
  // Identifier | Expression
  public AbstractAssignPathExpression property;
  public boolean computed;

  @Override
  public void compile() throws IException {

  }

  @Override
  public Object run(Context context) throws IException {
    List<Object> path = new ArrayList<>();
    object.addMemberPath(path);
    property.addMemberPath(path);
    return object.getVariableValue(context, path);
  }

  @Override
  public void addMemberPath(List<Object> path) throws IException {
      object.addMemberPath(path);
      property.addMemberPath(path);
  }
}
