package org.qiuer.ast.expression.function.array;

import org.qiuer.ast.assign.AssignKind;
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

  private List<IExpression> supportedArguments = new ArrayList<>();
  @Override
  public void compile() {
    this.id = new Identifier("forEach");
    this.params = new ArrayList<>();
    this.params.add(new Identifier("_list"));
    this.params.add(new Identifier("_func"));

    this.supportedArguments.add(new Identifier("_item"));
    this.supportedArguments.add(new Identifier("_index"));
  }

  @Override
  public Object run(Context context) throws IException {
    if(this.params != null && this.params.size() == 2){
      List<Object> list = EValidate.cast(this.params.get(0).run(context), List.class);
      ArrowFunctionExpression function = EValidate.cast(this.params.get(1).run(context), ArrowFunctionExpression.class);

      long index = 0;
      for (Object item : list) {
        context.declareVariable("_item", item, AssignKind.CONST);
        context.declareVariable("_index", index, AssignKind.CONST);
        CallExpression callExpression = new CallExpression(function, supportedArguments.subList(0, function.params.size()));
        callExpression.compile();
        callExpression.run(context);
        index++;
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
