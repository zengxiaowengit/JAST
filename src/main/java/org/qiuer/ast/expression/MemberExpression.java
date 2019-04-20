package org.qiuer.ast.expression;

import org.qiuer.ast.expression.function.Function;
import org.qiuer.ast.expression.function.SystemFunction;
import org.qiuer.core.Context;
import org.qiuer.exception.IException;

import java.util.ArrayList;
import java.util.List;

public class MemberExpression extends AbstractAssignPathExpression{
  public String type = "MemberExpression";
  public AbstractAssignPathExpression object;
  // Identifier | Expression
  public AbstractAssignPathExpression property;
  public boolean computed;

  @Override
  public void compile() throws IException {

  }

  @Override
  public Object run(Context context) throws IException {
    List<String> path = new ArrayList<>();
    object.addMemberPath(path);
    property.addMemberPath(path);
    Object ret = object.getVariableValue(context, path);
    if(ret instanceof SystemFunction && ((SystemFunction) ret).allowPropCall()){//特例，通过属性访问的函数。
      SystemFunction function = (SystemFunction) ret;
      List<IExpression> arguments = new ArrayList<>();
      arguments.add(this.object);
      CallExpression callExpression = new CallExpression(function, arguments);
      callExpression.compile();
      return callExpression.run(context);
    }
    return ret;
  }

  @Override
  public void addMemberPath(List<String> path) throws IException {
      object.addMemberPath(path);
      property.addMemberPath(path);
  }

  /**
   * 获取成员函数
   * @param context
   * @throws IException
   */
  public Function getFunction(Context context) throws IException {
    List<String> path = new ArrayList<>();
    object.addMemberPath(path);
    Class type = object.run(context).getClass();
    property.addMemberPath(path);
    return context.getFunction(type, path.get(path.size() - 1));
  }
}
