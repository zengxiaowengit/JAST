package org.qiuer.ast.expression;

import org.qiuer.core.Context;
import org.qiuer.exception.IException;

import java.util.ArrayList;
import java.util.List;

public class MemberExpression extends AbstractAssignPathExpression implements Expression{
  String type = "MemberExpression";
  AbstractAssignPathExpression object;
  // Identifier | Expression
  AbstractAssignPathExpression property;
  boolean computed;

  @Override
  public Object run(Context context) throws IException {
    List<Object> path = new ArrayList<>();
    object.addMemberPath(path);
    property.addMemberPath(path);
    return object.getValue(context, path);
  }

  @Override
  public void addMemberPath(List<Object> path) throws IException {
      object.addMemberPath(path);
      property.addMemberPath(path);
  }
}
