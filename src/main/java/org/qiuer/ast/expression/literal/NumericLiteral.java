package org.qiuer.ast.expression.literal;

import org.qiuer.core.Context;
import org.qiuer.exception.EValidate;
import org.qiuer.exception.IException;

import java.math.BigDecimal;
import java.util.List;

public class NumericLiteral extends Literal {
  public String type = "StringLiteral";
  public Object value;

  public NumericLiteral(){}
  public NumericLiteral(Number value) throws IException {
    this.value = value;
    this.compile();
  }

  @Override
  public void compile() throws IException {
    EValidate.notNull(value);
    // 整数用long计算，小数用decimal。保证正确性和计算不溢出。
    if(value.toString().contains(".")){
      value = new BigDecimal(value.toString());
    }else {
      value = Long.parseLong(value.toString());
    }
  }

  @Override
  public Object run(Context context) throws IException {
    return value;
  }

  @Override
  public void addMemberPath(List<String> path) {
    path.add(value.toString());
  }
}
