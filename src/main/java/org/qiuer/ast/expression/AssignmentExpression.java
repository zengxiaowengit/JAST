package org.qiuer.ast.expression;

import org.apache.commons.lang.StringUtils;
import org.qiuer.ast.assign.AssignmentOperator;
import org.qiuer.ast.expression.function.Function;
import org.qiuer.ast.pattern.IPattern;
import org.qiuer.core.Context;
import org.qiuer.exception.Const;
import org.qiuer.exception.ERuntime;
import org.qiuer.exception.EValidate;
import org.qiuer.exception.IException;

import java.util.ArrayList;
import java.util.List;

public class AssignmentExpression extends Expression{
  public String type = "AssignmentExpression";
  public String operator;
  public IPattern left;
  public IExpression right;

  @Override
  public void compile() throws IException {
    EValidate.notNull(operator);
    EValidate.notNull(left);
    EValidate.notNull(right);
  }

  @Override
  public Object run(Context context) throws IException {
    AssignmentOperator op = AssignmentOperator.parse(operator);
    switch (op){
      case EQUAL:
        if (left instanceof AbstractAssignPathExpression){
          AbstractAssignPathExpression assignPathExpression = (AbstractAssignPathExpression) left;
          List<String> path = new ArrayList<>();
          ((AbstractAssignPathExpression) left).addMemberPath(path);

          if(right instanceof Function)
            context.declareFunction(StringUtils.join(path, "."), (Function) right);
          else
            assignPathExpression.updateVariableValue(context, path, right.run(context));
        }else {
          throw new ERuntime(Const.EXCEPTION.UNSUPPORTED_EXPRESSION,"类型暂不支持声明：" + left);
        }
        break;
      default: throw new ERuntime(Const.EXCEPTION.UNSUPPORTED_EXPRESSION,"暂未支持的声明操作符：" + operator);
    }
    return null;
  }
}
