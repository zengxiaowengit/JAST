package org.qiuer.ast.expression;

import org.qiuer.ast.expression.literal.NumericLiteral;
import org.qiuer.core.Context;
import org.qiuer.exception.Const;
import org.qiuer.exception.ERuntime;
import org.qiuer.exception.EValidate;
import org.qiuer.exception.IException;

public class UpdateExpression extends Expression{
  public String type = "UpdateExpression";
  public String operator;
  public IExpression argument;
  public boolean prefix;

  public UpdateOperator updateOperator;
  private AssignmentExpression updateExpr;

  @Override
  public void compile() throws IException {
    EValidate.notNull(operator);
    EValidate.notNull(argument);
    updateOperator = UpdateOperator.parse(operator);
    EValidate.notNull(updateOperator);

    // a++ => a = a + 1
    updateExpr = new AssignmentExpression();
    BinaryExpression binaryExpression = new BinaryExpression();
    binaryExpression.left = argument;
    if (updateOperator == UpdateOperator.PLUS) binaryExpression.operator = "+";
    else binaryExpression.operator = "-";
    NumericLiteral literal = new NumericLiteral(1);
    binaryExpression.right = literal;
    updateExpr.left = argument;
    updateExpr.operator = "=";
    updateExpr.right = binaryExpression;
    literal.compile();
    binaryExpression.compile();
    updateExpr.compile();
  }

  @Override
  public Object run(Context context) throws IException {
    return updateExpr.run(context);

    // 经过测试，上一种效果更好。
    /*Object value = argument.run(context);
    if(value instanceof Long){
      long arg = (long) value;
      if (updateOperator == UpdateOperator.PLUS) ++arg;
      else --arg;
      context.updateVariable(((Identifier)argument).name, arg);
      return arg;
    }else if(value instanceof BigDecimal){
      BigDecimal arg = (BigDecimal)value;
      if (updateOperator == UpdateOperator.PLUS)
        arg = arg.add(new BigDecimal(1));
      else
        arg = arg.subtract(new BigDecimal(1));
      context.updateVariable(((Identifier)argument).name, arg);
      return arg;
    }else
      throw new ERuntime(Const.EXCEPTION.UNSUPPORTED_OPERATION, "UpdateExpression更新的对象不是数值类型");*/
  }

  public enum UpdateOperator {
    PLUS, MINUS;

    public static UpdateOperator parse(String str) throws IException{
      if(str == null) return null;
      switch (str.trim()){
        case "++": return PLUS;
        case "--": return MINUS;
        default: throw new ERuntime(Const.EXCEPTION.UNKNOWN_IDENTIFIER, "更新操作符只能为++或者--");
      }
    }
  }
}
