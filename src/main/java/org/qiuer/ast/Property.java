package org.qiuer.ast;

import org.qiuer.ast.expression.Expression;
import org.qiuer.core.Context;
import org.qiuer.exception.IException;

public class Property implements Node {
  String type = "Property";
  //  Literal | Identifier
  Expression key;
  Expression value;
  // init | get | set
  String kind;

  @Override
  public Object run(Context context) throws IException {
    return null;
  }
}