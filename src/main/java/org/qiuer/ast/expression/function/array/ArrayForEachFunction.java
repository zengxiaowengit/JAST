package org.qiuer.ast.expression.function.array;

import org.qiuer.ast.expression.CallExpression;
import org.qiuer.ast.expression.IExpression;
import org.qiuer.ast.expression.Identifier;
import org.qiuer.ast.expression.function.ArrowFunctionExpression;
import org.qiuer.ast.expression.function.SystemFunction;
import org.qiuer.ast.pattern.IPattern;
import org.qiuer.core.Context;
import org.qiuer.exception.Const;
import org.qiuer.exception.ERuntime;
import org.qiuer.exception.EValidate;
import org.qiuer.exception.IException;
import org.qiuer.util.JsonUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * list.push(element)
 */
public class ArrayForEachFunction extends SystemFunction {
  public String type = "ArrayForEachFunction";

  @Override
  public void compile() {
    this.id = new Identifier("forEach");
    this.params = new ArrayList<>();
    this.params.add(new Identifier("list"));
    this.params.add(new Identifier("function"));
  }

  @Override
  public Object run(Context context) throws IException {
    if(this.params != null && this.params.size() == 2){
      List<Object> list = EValidate.cast(this.params.get(0).run(context), List.class);
      ArrowFunctionExpression function = EValidate.cast(this.params.get(1).run(context), ArrowFunctionExpression.class);
      for (Object item : list) {
        //TODO 这里的代码和CallExpression的代码很像。怎么改造一下。
        List<IExpression> arguments = new ArrayList<>();
        if (function.params.size() > 0) {
          IPattern param1 = function.params.get(0);
          if (param1 instanceof Identifier) {
            arguments.add((Identifier) param1);
            context.updateVariable(((Identifier) param1).name, item);
          } else
            throw new ERuntime(Const.EXCEPTION.UNSUPPORTED_EXPRESSION, "箭头函数的参数名称只能是简单标识符");
        }
        CallExpression callExpression = new CallExpression(function, arguments);
        callExpression.compile();
        callExpression.run(context);
      }
    }else {
      throw new ERuntime(Const.EXCEPTION.WRONG_PARAMS_ON_CALL,"调用" + this.type + "函数参数错误：" + JsonUtil.toJson(this.params));
    }
    return null;
  }

  @Override
  public Class registerToOrNull() {
    return List.class;
  }

  @Override public boolean allowPropCall() { return false; }

}
