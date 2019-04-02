package org.qiuer.ast.expression;


import java.util.List;

public class ArrayExpression implements Expression {
  String type = "ArrayExpression";
  List<Expression> elements;
}
