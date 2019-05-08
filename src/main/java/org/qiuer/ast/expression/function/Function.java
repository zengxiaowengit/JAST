package org.qiuer.ast.expression.function;

import org.qiuer.ast.INode;
import org.qiuer.ast.assign.AssignKind;
import org.qiuer.ast.expression.Expression;
import org.qiuer.ast.expression.IExpression;
import org.qiuer.ast.expression.Identifier;
import org.qiuer.ast.pattern.IPattern;
import org.qiuer.core.Context;
import org.qiuer.exception.Const;
import org.qiuer.exception.ERuntime;
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

  public void prepareParams(Context context, List<IExpression> arguments) throws IException {
    int size = arguments.size();
    // 给的参数比定义的多，报错
    if(size > params.size())
      throw new ERuntime(Const.EXCEPTION.TOO_MANY_ARGUMENTS, "调用函数：" + id.name + "传入太多参数");
    // 初始化函数的参数
    for (int i = 0; i < size; i++) {
      IPattern param = this.params.get(i);
      IExpression argument = arguments.get(i);
      if (param instanceof Identifier) {
        Object value;
        //函数作为参数，只是定义。不做调用。
        if (argument instanceof ArrowFunctionExpression || argument instanceof FunctionExpression) value = argument;
        else value = argument.run(context);
        context.declareVariable(((Identifier) param).name, value, AssignKind.CONST);
      } else {
        throw new ERuntime(Const.EXCEPTION.UNKNOWN_ERROR, "函数: " + id.name + "定义的参数名称只能是简单的标识符Identifier");
      }
    }
  }
}