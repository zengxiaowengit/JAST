package org.qiuer.ast.expression;

import org.qiuer.ast.Property;
import org.qiuer.core.Context;
import org.qiuer.exception.IException;

import java.util.List;

public class ObjectExpression implements Expression{
  String type = "ObjectExpression";
  List<Property> properties;

  @Override
  public Object run(Context context) throws IException {
    return null;
  }
}
