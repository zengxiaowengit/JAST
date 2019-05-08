package org.qiuer.ast.expression.function.array;

import org.qiuer.ast.assign.AssignKind;
import org.qiuer.ast.expression.CallExpression;
import org.qiuer.ast.expression.IExpression;
import org.qiuer.ast.expression.Identifier;
import org.qiuer.ast.expression.function.ArrowFunctionExpression;
import org.qiuer.ast.expression.function.SystemFunction;
import org.qiuer.core.Context;
import org.qiuer.exception.EValidate;
import org.qiuer.exception.IException;
import org.qiuer.util.JsonUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * list.push(element)
 */
public class ArrayFilterFunction extends SystemFunction {
  public String type = "ArrayFilterFunction";

  private List<IExpression> supportedArguments = new ArrayList<>();
  @Override
  public void compile() {
    this.id = new Identifier("filter");
    this.params = new ArrayList<>();
    this.params.add(new Identifier("_list"));
    this.params.add(new Identifier("_func"));

    this.supportedArguments.add(new Identifier("_item"));
    this.supportedArguments.add(new Identifier("_index"));
  }

  @Override
  public Object run(Context context) throws IException {
    EValidate.assertTrue(this.params != null && this.params.size() == 2, "调用" + this.type + "函数参数错误：" + JsonUtil.toJson(this.params));
    List<Object> list = EValidate.cast(this.params.get(0).run(context), List.class);
    ArrowFunctionExpression function = EValidate.cast(this.params.get(1).run(context), ArrowFunctionExpression.class);
    List<Object> result = new ArrayList<>();

    long index = 0;
    for (Object item : list) {
      context.declareVariable("_item", item, AssignKind.CONST);
      context.declareVariable("_index", index, AssignKind.CONST);
      CallExpression callExpression = new CallExpression(function, supportedArguments.subList(0, function.params.size()));
      callExpression.compile();
      if(EValidate.cast(callExpression.run(context), Boolean.class)){
        result.add(callExpression.run(context));
      }
      index++;
    }

    return result;
  }

  @Override
  public Class registerToOrNull() {
    return List.class;
  }

  @Override public boolean allowPropCall() { return false; }

}
