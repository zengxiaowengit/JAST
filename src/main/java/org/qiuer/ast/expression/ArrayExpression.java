package org.qiuer.ast.expression;


import org.qiuer.core.Context;
import org.qiuer.exception.IException;

import java.util.ArrayList;
import java.util.List;

public class ArrayExpression extends Expression {
  String type = "ArrayExpression";
  List<Expression> elements;

  @Override
  public void compile() throws IException {

  }

  @Override
  public Object run(Context context) throws IException {
    List<Object> list = new ArrayList<>();
    for (Expression expression: elements) {
      list.add(expression.run(context));
    }
    return list;
  }

}
