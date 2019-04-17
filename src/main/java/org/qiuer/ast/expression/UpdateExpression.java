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
  @Override
  public void compile() throws IException {
    EValidate.notNull(operator);
    EValidate.notNull(argument);
    updateOperator = UpdateOperator.parse(operator);
    EValidate.notNull(updateOperator);
  }

  @Override
  public Object run(Context context) throws IException {
    // a++ => a = a + 1
    AssignmentExpression assignmentExpression = new AssignmentExpression();
    BinaryExpression binaryExpression = new BinaryExpression();
    binaryExpression.left = argument;
    if (updateOperator == UpdateOperator.PLUS) binaryExpression.operator = "+";
    else binaryExpression.operator = "-";
    NumericLiteral literal = new NumericLiteral(1);
    binaryExpression.right = literal;
    assignmentExpression.left = argument;
    assignmentExpression.operator = "=";
    assignmentExpression.right = binaryExpression;
    literal.compile();
    binaryExpression.compile();
    assignmentExpression.compile();

    return assignmentExpression.run(context);
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
