package org.qiuer.ast.expression.function.array;

import org.qiuer.ast.expression.Identifier;
import org.qiuer.ast.expression.function.SystemFunction;
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
public class ArrayLengthFunction extends SystemFunction {

  @Override
  public void compile() {
    this.id = new Identifier("length");
    this.params = new ArrayList<>();
    this.params.add(new Identifier("list"));
  }

  @Override
  public Object run(Context context) throws IException {
    EValidate.assertTrue(this.params != null && this.params.size() == 1,
            "调用" + this.type + "函数参数错误：" + JsonUtil.toJson(this.params));
    List<Object> list = EValidate.cast(this.params.get(0).run(context), List.class);
    return list.size();
  }

  @Override
  public Class registerToOrNull() {
    return List.class;
  }

  @Override public boolean allowPropCall() { return true; }

}
