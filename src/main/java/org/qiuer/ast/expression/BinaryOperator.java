package org.qiuer.ast.expression;

import org.qiuer.exception.Const;
import org.qiuer.exception.ERuntime;

import java.math.BigDecimal;

/**
 * 二元操作符
 */
public enum BinaryOperator {
  /*"==" | "!=" | "===" | "!=="
          | "<" | "<=" | ">" | ">="
          | "<<" | ">>"
          | "+" | "-" | "*" | "/" | "%"


          暂不支持：
          | ">>>"
          | "|" | "^" | "&" | "in"
          | "instanceof" | ".."*/
  DOUBLE_EQUAL,
  NOT_EQUAL,
  TRIPLE_EQUAL,
  NOT_DOUBLE_EQUAL,
  LESS,
  LESS_EQUAL,
  GREAT,
  GREAT_EQUAL,
  LEFT_SHIFT,
  RIGHT_SHIFT,
  PLUS,
  MINUS,
  MULTIPLY,
  DIVIDE,
  MOD,
  ;

  public static BinaryOperator parse(String str) throws ERuntime {
    if (str == null){
      return null;
    }
    switch (str.trim()){
      case "==": return DOUBLE_EQUAL;// 值是否等。会转换为同类型比较。
      case "!=": return NOT_EQUAL; // 对象是否同一个。不会转换类型。
      case "===": return TRIPLE_EQUAL;
      case "!==": return NOT_DOUBLE_EQUAL;
      case "<": return LESS;
      case "<=": return LESS_EQUAL;
      case ">": return GREAT;
      case ">=": return GREAT_EQUAL;
      case "<<": return LEFT_SHIFT;
      case ">>": return RIGHT_SHIFT;

      case "+": return PLUS;
      case "-": return MINUS;
      case "*": return MULTIPLY;
      case "/": return DIVIDE;
      case "%": return MOD;
      default: throw new ERuntime(Const.EXCEPTION.UNKNOWN_IDENTIFIER, "不支持的操作符:" + str);
    }
  }
}

abstract class BinaryOperation<T>{
  //TODO 有空再实现。
//  public abstract boolean DOUBLE_EQUAL(Object left , Object right) throws ERuntime ;
//  public abstract boolean NOT_EQUAL(Object left, Object right) throws ERuntime ;
//  public abstract boolean TRIPLE_EQUAL(Object left, Object right) throws ERuntime ;
//  public abstract boolean NOT_DOUBLE_EQUAL(Object left, Object right) throws ERuntime ;
  public abstract boolean LESS(Object left, Object right) throws ERuntime;
  public abstract boolean LESS_EQUAL(Object left, Object right) throws ERuntime ;
  public abstract boolean GREAT(Object left, Object right) throws ERuntime ;
  public abstract boolean GREAT_EQUAL(Object left, Object right) throws ERuntime ;
  public abstract T MINUS(Object left, Object right) throws ERuntime;
  public abstract T MULTIPLY(Object left, Object right) throws ERuntime;
  public abstract BigDecimal DIVIDE(Object left, Object right) throws ERuntime;
  public abstract T MOD(Object left, Object right) throws ERuntime;
//  public abstract void LEFT_SHIFT(Object left, Object right) throws ERuntime ;
//  public abstract void RIGHT_SHIFT(Object left, Object right) throws ERuntime ;

  protected static final int DIVIDE_SCALE = 10;

  public BigDecimal DivideDecimal(BigDecimal a, BigDecimal b){
    return a.divide(b, DIVIDE_SCALE, BigDecimal.ROUND_HALF_UP);
  }
  public abstract T PLUS(Object left, Object right) throws ERuntime;

  protected BigDecimal toBigDecimal(Object object) throws ERuntime {
    if(object instanceof BigDecimal){
      return (BigDecimal) object;
    }
    try {
      return new BigDecimal(String.valueOf(object));
    }catch (Exception e){
      throw new ERuntime(Const.EXCEPTION.TYPE_CAST_ERROR, object.getClass().getSimpleName() + "类型不能转换为数值类型");
    }
  }

  protected Long toLong(Object object) throws ERuntime {
    if(object instanceof Long){
      return (Long) object;
    }
    try {
      return new Long(String.valueOf(object));
    }catch (Exception e){
      throw new ERuntime(Const.EXCEPTION.TYPE_CAST_ERROR, object.getClass().getSimpleName() + "类型不能转换为Long类型");
    }
  }
}

class LongBinaryOperation extends BinaryOperation<Long>{

  @Override
  public boolean LESS(Object left, Object right) throws ERuntime {
    return toLong(left) < toLong(right);
  }

  @Override
  public boolean LESS_EQUAL(Object left, Object right) throws ERuntime {
    return toLong(left) <= toLong(right);
  }

  @Override
  public boolean GREAT(Object left, Object right) throws ERuntime {
    return toLong(left) > toLong(right);
  }

  @Override
  public boolean GREAT_EQUAL(Object left, Object right) throws ERuntime {
    return toLong(left) >= toLong(right);
  }

  @Override
  public Long PLUS(Object left, Object right) throws ERuntime {
    return toLong(left) + toLong(right);
  }

  @Override
  public Long MINUS(Object left, Object right) throws ERuntime {
    return toLong(left) - toLong(right);
  }

  @Override
  public Long MULTIPLY(Object left, Object right) throws ERuntime {
    return toLong(left) * toLong(right);
  }

  @Override
  public BigDecimal DIVIDE(Object left, Object right) throws ERuntime {
    return super.DivideDecimal(toBigDecimal(left), toBigDecimal(right));
  }

  @Override
  public Long MOD(Object left, Object right) throws ERuntime {
    return toLong(left) % toLong(right);
  }

}

class BigDecimalBinaryOperation extends BinaryOperation<BigDecimal>{

  @Override
  public BigDecimal PLUS(Object left, Object right) throws ERuntime {
    return toBigDecimal(left).add(toBigDecimal(right));
  }

  @Override
  public boolean LESS(Object left, Object right) throws ERuntime {
    return toBigDecimal(left).compareTo(toBigDecimal(right)) < 0;
  }

  @Override
  public boolean LESS_EQUAL(Object left, Object right) throws ERuntime {
    return toBigDecimal(left).compareTo(toBigDecimal(right)) <= 0;
  }

  @Override
  public boolean GREAT(Object left, Object right) throws ERuntime {
    return toBigDecimal(left).compareTo(toBigDecimal(right)) > 0;
  }

  @Override
  public boolean GREAT_EQUAL(Object left, Object right) throws ERuntime {
    return toBigDecimal(left).compareTo(toBigDecimal(right)) >= 0;
  }

  @Override
  public BigDecimal MINUS(Object left, Object right) throws ERuntime {
    return toBigDecimal(left).subtract(toBigDecimal(right));
  }

  @Override
  public BigDecimal MULTIPLY(Object left, Object right) throws ERuntime {
    return toBigDecimal(left).multiply(toBigDecimal(right));
  }

  @Override
  public BigDecimal DIVIDE(Object left, Object right) throws ERuntime {
    return super.DivideDecimal(toBigDecimal(left), toBigDecimal(right));
  }

  @Override
  public BigDecimal MOD(Object left, Object right) throws ERuntime {
    BigDecimal a = toBigDecimal(left);
    BigDecimal b = toBigDecimal(right);
    BigDecimal[] remainder = a.divideAndRemainder(b);
    if(remainder == null || remainder.length == 0){
      throw new ERuntime(Const.EXCEPTION.UNSUPPORTED_OPERATION, a.toPlainString() + "%" + b.toPlainString() + "失败");
    }
    return remainder[remainder.length - 1];
  }

}