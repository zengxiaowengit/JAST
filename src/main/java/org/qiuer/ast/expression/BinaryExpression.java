package org.qiuer.ast.expression;

import org.qiuer.core.Context;
import org.qiuer.exception.Const;
import org.qiuer.exception.ERuntime;
import org.qiuer.exception.EValidate;
import org.qiuer.exception.IException;

import java.math.BigDecimal;

public class BinaryExpression extends Expression {
  public String type = "BinaryExpression";
  public String operator;
  public Expression left;
  public Expression right;

  public BinaryOperator binaryOperator;

  protected static LongBinaryOperation longBinaryOperation = new LongBinaryOperation();
  protected static BigDecimalBinaryOperation decimalBinaryOperation = new BigDecimalBinaryOperation();

  @Override
  public void compile() throws IException {
    EValidate.notNull(operator);
    EValidate.notNull(left);
    EValidate.notNull(right);
    binaryOperator = BinaryOperator.parse(operator);
  }

  @Override
  public Object run(Context context) throws IException {
    Object a = this.left.run(context);
    Object b = this.right.run(context);

    BinaryOperation operation = getOperation(a, b);
    switch (binaryOperator){
      case PLUS: return operation.PLUS(a, b);
      case MINUS: return operation.MINUS(a, b);
      case MULTIPLY:  return operation.MULTIPLY(a, b);
      case DIVIDE:
        BigDecimal ret = operation.DIVIDE(a, b);
        if(ret.compareTo(new BigDecimal(ret.longValue())) == 0){//能除尽。返回long。
          return ret.longValue();
        }else
          return ret;
      case MOD: return operation.MOD(a, b);
      default:
        throw new ERuntime(Const.EXCEPTION.UNSUPPORTED_OPERATION, "暂不支持" + a.getClass().getSimpleName()
                + "和" + b.getClass().getSimpleName() + "类型的操作：" + operator);
    }
  }

  protected BinaryOperation getOperation(Object a, Object b) throws ERuntime {
    if(a instanceof Long && b instanceof Long){
      return longBinaryOperation;
    }else if(b instanceof BigDecimal || b instanceof BigDecimal){
      return decimalBinaryOperation;
    }else {
      throw new ERuntime(Const.EXCEPTION.UNSUPPORTED_OPERATION, "暂不支持" + a.getClass().getSimpleName()
              + "和" + b.getClass().getSimpleName() + "类型的操作：" + operator);
    }
  }

}
