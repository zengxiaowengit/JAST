package org.qiuer.ast.expression;

import org.qiuer.ast.assign.AssignmentOperator;
import org.qiuer.ast.pattern.Pattern;
import org.qiuer.core.Context;
import org.qiuer.exception.Const;
import org.qiuer.exception.ERuntime;
import org.qiuer.exception.IException;

import java.util.ArrayList;
import java.util.List;

public class AssignmentExpression implements Expression{
  String type = "AssignmentExpression";
  String operator;
  Pattern left;
  Expression right;

  @Override
  public Object run(Context context) throws IException {
    AssignmentOperator op = AssignmentOperator.parse(operator);
    switch (op){
      case EQUAL:
        Object value = right.run(context);
        if (left instanceof AbstractAssignPathExpression){
          AbstractAssignPathExpression assignPathExpression = (AbstractAssignPathExpression) left;
          List<Object> path = new ArrayList<>();
          ((AbstractAssignPathExpression) left).addMemberPath(path);
          assignPathExpression.updateValue(context, path, value);
        }else {
          throw new ERuntime(Const.EXCEPTION.FRAME.UNSUPPORTED_EXPRESSION,"类型暂不支持声明：" + left);
        }
        break;
      default:throw new ERuntime(Const.EXCEPTION.FRAME.UNSUPPORTED_EXPRESSION,"暂未支持的声明操作符：" + operator);
    }
    return null;
  }
}
