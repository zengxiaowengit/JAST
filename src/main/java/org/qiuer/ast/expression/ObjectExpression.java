package org.qiuer.ast.expression;

import org.qiuer.ast.ObjectProperty;
import org.qiuer.core.Context;
import org.qiuer.exception.Const;
import org.qiuer.exception.ERuntime;
import org.qiuer.exception.IException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ObjectExpression extends Expression{
  public String type = "ObjectExpression";
  public List<ObjectProperty> properties;

  @Override
  public void compile() throws IException {

  }

  @Override
  public Object run(Context context) throws IException {
    Map<String, Object> map = new HashMap<>();
    for (ObjectProperty property : properties) {
      if(property.key instanceof Identifier){
        map.put(((Identifier) property.key).name, property.value.run(context));
      }else {
        throw new ERuntime(Const.EXCEPTION.UNSUPPORTED_EXPRESSION, "不支持的表达式：" + property.key.toString());
      }
    }
    return map;
  }
}
