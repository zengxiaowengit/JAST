package org.qiuer.ast.expression.function;

import org.qiuer.ast.INode;
import org.qiuer.ast.expression.Expression;
import org.qiuer.ast.expression.IExpression;
import org.qiuer.ast.expression.Identifier;
import org.qiuer.ast.pattern.IPattern;
import org.qiuer.exception.EValidate;
import org.qiuer.exception.IException;

import java.util.ArrayList;
import java.util.List;

public abstract class Function extends Expression{
  public String type = "Function";
  public Identifier id;
  public List<IPattern> params = new ArrayList<>();
  public List<IExpression> defaults = new ArrayList<>(); // TODO 函数给默认值。未实现。
  public Identifier rest;
  public INode body; // BlockStatement || Expression
  public boolean generator;
  public boolean expression;
}