package org.qiuer.ast.expression;

import org.qiuer.ast.Property;

import java.util.List;

public class ObjectExpression implements Expression{
  String type = "ObjectExpression";
  List<Property> properties;
}
