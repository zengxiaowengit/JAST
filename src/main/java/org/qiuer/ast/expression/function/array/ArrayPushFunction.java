package org.qiuer.ast.expression.function.array;

import org.qiuer.ast.expression.Identifier;
import org.qiuer.ast.expression.function.CustomFunction;
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
public class ArrayPushFunction extends CustomFunction {
  public String type = "ArrayPushFunction";

  @Override
  public void compile() {
    this.id = new Identifier("push");
    this.params = new ArrayList<>();
    this.params.add(new Identifier("list"));
    this.params.add(new Identifier("element"));
  }

  @Override
  public Object run(Context context) throws IException {
    if(this.params != null && this.params.size() == 2){
      List<Object> list = EValidate.cast(this.params.get(0).run(context), List.class);
      Object element = this.params.get(1).run(context);
      list.add(element);
      return element;
    }else {
      throw new ERuntime(Const.EXCEPTION.WRONG_PARAMS_ON_CALL,"调用" + this.type + "函数参数错误：" + JsonUtil.toJson(this.params));
    }
  }

  @Override public String getName(){
    return "push";
  }

  @Override
  public Class registerTo() {
    return List.class;
  }

  @Override public boolean allowPropCall() { return false; }

}
