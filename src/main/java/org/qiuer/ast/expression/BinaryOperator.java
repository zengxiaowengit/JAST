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
      case "==": return DOUBLE_EQUAL;
      case "!=": return NOT_EQUAL;
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

// A: 操作数1类型，B，操作数2类型。R：返回值类型。
abstract class BinaryOperation<A, B, R>{

  protected A left;
  protected B right;

  BinaryOperation(A left, B right){
    this.left = left;
    this.right = right;
  }
  // ==: 值是否相等。toString, equals
  public abstract boolean DOUBLE_EQUAL() throws ERuntime ;
  public abstract boolean NOT_EQUAL() throws ERuntime ;
  // ===: 类型和值都必需相等。equals
  public abstract boolean TRIPLE_EQUAL() throws ERuntime ;
  public abstract boolean NOT_DOUBLE_EQUAL() throws ERuntime ;
  public abstract boolean LESS() throws ERuntime;
  public abstract boolean LESS_EQUAL() throws ERuntime ;
  public abstract boolean GREAT() throws ERuntime ;
  public abstract boolean GREAT_EQUAL() throws ERuntime ;
  public abstract R PLUS() throws ERuntime;
  public abstract R MINUS() throws ERuntime;
  public abstract R MULTIPLY() throws ERuntime;
  public abstract R DIVIDE() throws ERuntime;
  public abstract R MOD() throws ERuntime;
//  public abstract void LEFT_SHIFT() throws ERuntime ;
//  public abstract void RIGHT_SHIFT() throws ERuntime ;

  protected static final int DIVIDE_SCALE = 10;
}

class LongBinaryOperation extends BinaryOperation<Long, Long, Long>{

  LongBinaryOperation(Long left, Long right) {
    super(left, right);
  }

  @Override
  public boolean DOUBLE_EQUAL() throws ERuntime {
    return this.left.equals(this.right);
  }

  @Override
  public boolean NOT_EQUAL() throws ERuntime {
    return !(this.left.equals(this.right));
  }

  @Override
  public boolean TRIPLE_EQUAL() throws ERuntime {
    return this.left.equals(this.right);
  }

  @Override
  public boolean NOT_DOUBLE_EQUAL() throws ERuntime {
    return !(this.left.equals(this.right));
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

class BigDecimalBinaryOperation extends BinaryOperation<BigDecimal, BigDecimal, BigDecimal>{

  BigDecimalBinaryOperation(BigDecimal left, BigDecimal right) {
    super(left, right);
  }

  @Override
  public boolean DOUBLE_EQUAL() throws ERuntime {
    return this.left.equals(this.right);
  }

  @Override
  public boolean NOT_EQUAL() throws ERuntime {
    return !this.left.equals(this.right);
  }

  @Override
  public boolean TRIPLE_EQUAL() throws ERuntime {
    return this.left.equals(this.right);
  }

  @Override
  public boolean NOT_DOUBLE_EQUAL() throws ERuntime {
    return !this.left.equals(this.right);
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