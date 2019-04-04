package org.qiuer.ast;

import org.qiuer.ast.expression.Expression;
import org.qiuer.core.Context;
import org.qiuer.exception.IException;

public class Property implements Node {
  public String type = "Property";
  //  Literal | Identifier
  public Expression key;
  public Expression value;
  // init | get | set
  public String kind;

  @Override
  public Object run(Context context) throws IException {
    return null;
  }
}