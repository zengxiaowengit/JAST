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

  protected T left;
  protected T right;

  BinaryOperation(T left, T right){
    this.left = left;
    this.right = right;
  }
  //TODO 有空再实现。
//  public abstract boolean DOUBLE_EQUAL(Object left , Object property) throws ERuntime ;
//  public abstract boolean NOT_EQUAL(Object left, Object property) throws ERuntime ;
//  public abstract boolean TRIPLE_EQUAL(Object left, Object property) throws ERuntime ;
//  public abstract boolean NOT_DOUBLE_EQUAL(Object left, Object property) throws ERuntime ;
  public abstract boolean LESS() throws ERuntime;
  public abstract boolean LESS_EQUAL() throws ERuntime ;
  public abstract boolean GREAT() throws ERuntime ;
  public abstract boolean GREAT_EQUAL() throws ERuntime ;
  public abstract T PLUS() throws ERuntime;
  public abstract T MINUS() throws ERuntime;
  public abstract T MULTIPLY() throws ERuntime;
  public abstract T DIVIDE() throws ERuntime;
  public abstract T MOD() throws ERuntime;
//  public abstract void LEFT_SHIFT(Object left, Object property) throws ERuntime ;

//  public abstract void RIGHT_SHIFT(Object left, Object property) throws ERuntime ;

  protected static final int DIVIDE_SCALE = 10;

/*
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
  }*/
}

class LongBinaryOperation extends BinaryOperation<Long>{

  LongBinaryOperation(Long left, Long right) {
    super(left, right);
  }

  @Override
  public boolean LESS() throws ERuntime {
    return this.left < this.right;
  }

  @Override
  public boolean LESS_EQUAL() throws ERuntime {
    return this.left <= this.right;
  }

  @Override
  public boolean GREAT() throws ERuntime {
    return this.left > this.right;
  }

  @Override
  public boolean GREAT_EQUAL() throws ERuntime {
    return this.left >= this.right;
  }

  @Override
  public Long PLUS() throws ERuntime {
    return this.left + this.right;
  }

  @Override
  public Long MINUS() throws ERuntime {
    return this.left - this.right;
  }

  @Override
  public Long MULTIPLY() throws ERuntime {
    return this.left * this.right;
  }

  @Override
  public Long DIVIDE() throws ERuntime {
    return this.left / this.right;
  }

  @Override
  public Long MOD() throws ERuntime {
    return this.left % this.right;
  }

}

class BigDecimalBinaryOperation extends BinaryOperation<BigDecimal>{

  BigDecimalBinaryOperation(BigDecimal left, BigDecimal right) {
    super(left, right);
  }

  private BigDecimal divideDecimal(BigDecimal a, BigDecimal b){
    return a.divide(b, DIVIDE_SCALE, BigDecimal.ROUND_HALF_UP);
  }

  @Override
  public BigDecimal PLUS() throws ERuntime {
    return this.left.add(this.right);
  }

  @Override
  public boolean LESS() throws ERuntime {
    return this.left.compareTo(this.right) < 0;
  }

  @Override
  public boolean LESS_EQUAL() throws ERuntime {
    return this.left.compareTo(this.right) <= 0;
  }

  @Override
  public boolean GREAT() throws ERuntime {
    return this.left.compareTo(this.right) > 0;
  }

  @Override
  public boolean GREAT_EQUAL() throws ERuntime {
    return this.left.compareTo(this.right) >= 0;
  }

  @Override
  public BigDecimal MINUS() throws ERuntime {
    return this.left.subtract(this.right);
  }

  @Override
  public BigDecimal MULTIPLY() throws ERuntime {
    return this.left.multiply(this.right);
  }

  @Override
  public BigDecimal DIVIDE() throws ERuntime {
    return divideDecimal(this.left, this.right);
  }

  @Override
  public BigDecimal MOD() throws ERuntime {
    BigDecimal a = this.left;
    BigDecimal b = this.right;
    BigDecimal[] remainder = a.divideAndRemainder(b);
    if(remainder == null || remainder.length == 0){
      throw new ERuntime(Const.EXCEPTION.UNSUPPORTED_OPERATION, a.toPlainString() + "%" + b.toPlainString() + "失败");
    }
    return remainder[remainder.length - 1];
  }

}