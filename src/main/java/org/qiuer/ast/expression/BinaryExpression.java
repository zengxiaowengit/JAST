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
  public IExpression left;
  public IExpression right;

  private BinaryOperator binaryOperator;

  @Override
  public void compile() throws IException {
    EValidate.notNull(operator);
    EValidate.notNull(left);
    EValidate.notNull(right);
    binaryOperator = BinaryOperator.parse(operator);
    //TODO 这里做改造。编译时知道操作符。但是不知道类型。操作符提前准备好。
  }

  @Override
  public Object run(Context context) throws IException {
    Object a = this.left.run(context);
    Object b = this.right.run(context);

    BinaryOperation operation = getOperation(a, b);
    switch (binaryOperator){
      case PLUS: return operation.PLUS();
      case MINUS: return operation.MINUS();
      case MULTIPLY: return operation.MULTIPLY();
      case DIVIDE: return operation.DIVIDE();
      case MOD: return operation.MOD();
      case LESS: return operation.LESS();
      case LESS_EQUAL:return operation.LESS_EQUAL();
      case GREAT: return operation.GREAT();
      case GREAT_EQUAL: return operation.GREAT_EQUAL();
      default:
        throw new ERuntime(Const.EXCEPTION.UNSUPPORTED_OPERATION, "暂不支持" + a.getClass().getSimpleName()
                + "和" + b.getClass().getSimpleName() + "类型的操作：" + operator);
    }
  }

  protected BinaryOperation getOperation(Object a, Object b) throws ERuntime {
    // long
    if(a instanceof Long && b instanceof Long){
      return new LongBinaryOperation(((Long) a), ((Long) b));
    }
    // decimal
    else if(a instanceof BigDecimal && b instanceof BigDecimal){
      return new BigDecimalBinaryOperation(((BigDecimal) a), ((BigDecimal) b));
    }
    // long & decimal
    else if(a instanceof Long && b instanceof BigDecimal){
      return new BigDecimalBinaryOperation(BigDecimal.valueOf((Long) a), ((BigDecimal) b));
    }else if(a instanceof BigDecimal && b instanceof Long){
      return new BigDecimalBinaryOperation(((BigDecimal) a), BigDecimal.valueOf((Long) b));
    // else
    }else {
      throw new ERuntime(Const.EXCEPTION.UNSUPPORTED_OPERATION, "暂不支持" + a.getClass().getSimpleName()
              + "和" + b.getClass().getSimpleName() + "类型的操作：" + operator);
    }
  }

}
