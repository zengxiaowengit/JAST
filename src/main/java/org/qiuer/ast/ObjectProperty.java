package org.qiuer.ast;

import org.qiuer.ast.expression.Expression;
import org.qiuer.core.Context;
import org.qiuer.exception.IException;

public class ObjectProperty extends Node {
  public String type = "ObjectProperty";
  //  Literal | Identifier
  public Expression key;
  public Expression value;
  // compile | getVariable | set
  public String kind;

  @Override
  public void compile() throws IException {

  }

  @Override
  public Object run(Context context) throws IException {
    return null;
  }
}