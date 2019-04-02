package org.qiuer.ast;

import org.qiuer.ast.expression.Expression;

public class Property implements Node {
  String type = "Property";
  //  Literal | Identifier
  Expression key;
  Expression value;
  // init | get | set
  String kind;
}