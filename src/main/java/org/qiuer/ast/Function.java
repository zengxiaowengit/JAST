package org.qiuer.ast;

import org.qiuer.ast.expression.Expression;
import org.qiuer.ast.pattern.Pattern;

import java.util.List;

public class Function implements Node{
  String type = "Function";
  Identifier id;
  List<Pattern> params;
  List<Expression> defaults;
  Identifier rest;
  Node body; // BlockStatement || Expression
  boolean generator;
  boolean expression;
}