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
  DOUBLE_EQUAL, NOT_EQUAL,TRIPLE_EQUAL, NOT_DOUBLE_EQUAL,
  LESS, LESS_EQUAL, GREAT, GREAT_EQUAL,
  LEFT_SHIFT, RIGHT_SHIFT,
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
//  public abstract boolean DOUBLE_EQUAL(T left , T right);
//  public abstract boolean NOT_EQUAL(T left, T right);
//  public abstract boolean TRIPLE_EQUAL(T left, T right);
//  public abstract boolean NOT_DOUBLE_EQUAL(T left, T right);
//  public abstract boolean LESS(T left, T right);
//  public abstract boolean LESS_EQUAL(T left, T right);
//  public abstract boolean GREAT(T left, T right);
//  public abstract boolean GREAT_EQUAL(T left, T right);
//  public abstract void LEFT_SHIFT(T left, T right);
//  public abstract void RIGHT_SHIFT(T left, T right);
  public abstract T PLUS(T left, T right);
  public abstract T MINUS(T left, T right);
  public abstract T MULTIPLY(T left, T right);
  public abstract BigDecimal DIVIDE(T left, T right);
  public abstract T MOD(T left, T right) throws ERuntime;

  protected static final int DIVIDE_SCALE = 10;

  public BigDecimal DivideDecimal(BigDecimal a, BigDecimal b){
    return a.divide(b, DIVIDE_SCALE, BigDecimal.ROUND_HALF_UP);
  }
}

class LongBinaryOperation extends BinaryOperation<Long>{

  @Override
  public Long PLUS(Long left, Long right) {
    return left + right;
  }

  @Override
  public Long MINUS(Long left, Long right) {
    return left - right;
  }

  @Override
  public Long MULTIPLY(Long left, Long right) {
    return left * right;
  }

  @Override
  public BigDecimal DIVIDE(Long left, Long right) {
    BigDecimal a = new BigDecimal(left);
    BigDecimal b = new BigDecimal(right);
    return super.DivideDecimal(a, b);
  }

  @Override
  public Long MOD(Long left, Long right) {
    return left % right;
  }
}

class BigDecimalBinaryOperation extends BinaryOperation<BigDecimal>{

  @Override
  public BigDecimal PLUS(BigDecimal left, BigDecimal right) {
    return left.add(right);
  }

  @Override
  public BigDecimal MINUS(BigDecimal left, BigDecimal right) {
    return left.subtract(right);
  }

  @Override
  public BigDecimal MULTIPLY(BigDecimal left, BigDecimal right) {
    return left.multiply(right);
  }

  @Override
  public BigDecimal DIVIDE(BigDecimal left, BigDecimal right) {
    return super.DivideDecimal(left, right);
  }

  @Override
  public BigDecimal MOD(BigDecimal left, BigDecimal right) throws ERuntime {
    BigDecimal[] remainder = left.divideAndRemainder(right);
    if(remainder == null || remainder.length == 0){
      throw new ERuntime(Const.EXCEPTION.UNSUPPORTED_OPERATION, left.toPlainString() + "%" + left.toPlainString() + "失败");
    }
    return remainder[remainder.length - 1];
  }
}