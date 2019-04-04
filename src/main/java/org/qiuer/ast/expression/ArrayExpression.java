package org.qiuer.ast.expression;


import org.qiuer.ast.Property;
import org.qiuer.core.Context;
import org.qiuer.exception.Const;
import org.qiuer.exception.ERuntime;
import org.qiuer.exception.IException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArrayExpression implements Expression {
  String type = "ArrayExpression";
  List<Expression> elements;

  @Override
  public Object run(Context context) throws IException {
    List<Object> list = new ArrayList<>();
    for (Expression expression: elements) {
      list.add(expression.run(context));
    }
    return list;
  }

}
