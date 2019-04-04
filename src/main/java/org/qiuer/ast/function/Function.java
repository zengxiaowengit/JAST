package org.qiuer.ast.function;

import org.qiuer.ast.expression.Identifier;
import org.qiuer.ast.Node;
import org.qiuer.ast.expression.Expression;
import org.qiuer.ast.pattern.Pattern;
import org.qiuer.core.Context;
import org.qiuer.exception.IException;

import java.util.List;

public class Function implements Node {
  String type = "Function";
  Identifier id;
  List<Pattern> params;
  List<Expression> defaults;
  Identifier rest;
  Node body; // BlockStatement || Expression
  boolean generator;
  boolean expression;

  @Override
  public Object run(Context context) throws IException {
    return null;
  }
}