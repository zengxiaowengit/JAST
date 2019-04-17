package org.qiuer.ast.expression;

import org.apache.commons.lang.StringUtils;
import org.qiuer.ast.expression.function.ArrowFunctionExpression;
import org.qiuer.ast.expression.function.Function;
import org.qiuer.ast.expression.function.SystemFunction;
import org.qiuer.ast.pattern.IPattern;
import org.qiuer.core.Context;
import org.qiuer.exception.*;

import java.util.ArrayList;
import java.util.List;

public class CallExpression extends Expression {
  String type = "CallExpression";
  IExpression callee;
  List<IExpression> arguments = new ArrayList<>();//调用参数

  private boolean compiled = false;

  public CallExpression(){}
  public CallExpression(IExpression callee, List<IExpression> arguments){
    this.callee = callee;
    this.arguments = arguments;
  }

  @Override
  public void compile() throws IException {
  }

  @Override
  public Object run(Context context) throws IException {
    try {
      context.enterBlock();

      Function function;
      if (callee instanceof Function) function = (Function) callee;
      else function = (Function) callee.run(context);
      EValidate.notNull(function);
      beforeRun(function);
      EValidate.assertTrue(function.params.size() == arguments.size(), "函数调用参数个数必须和函数定义参数个数相同");

      int size = arguments.size();
      // 初始化函数的参数
      for (int i = 0; i < size; i++) {
        IPattern param = function.params.get(i);
        IExpression argument = arguments.get(i);
        if (param instanceof Identifier) {
          Object value;
          if (argument instanceof ArrowFunctionExpression) value = argument; //箭头函数作为参数，只是定义。不做调用。
          else value = argument.run(context);
          context.updateVariable(((Identifier) param).name, value);
        } else {
          throw new ERuntime(1000, "函数定义的参数名称只能是简单的标识符Identifier");
        }
      }
      //调用函数
      return function.run(context);
    }catch (EReturn eReturn){
      return eReturn.getObject();
    }catch (IException e) {
      throw e;
    } catch (Exception e) {
      throw new ERuntime(Const.EXCEPTION.UNKNOWN_ERROR, e.getMessage());
    }finally {
      context.exitBlock();
    }
  }

  private void beforeRun(Function function){
    //系统注册函数，通过成员函数调用时，会把自身作为第一个参数传入。如：data.push(1)变成push(data, 1)的形式。
    //js只能在运行时编译。只能在第一次运行时做类似的事情。
    if(!compiled){
      synchronized (this){
        if(!compiled){ //防止并发问题。再检测一遍。
          if ((callee instanceof MemberExpression)
                  && function instanceof SystemFunction
                  && ((SystemFunction) function).registerToOrNull() != null) {
            arguments.add(0, ((MemberExpression) callee).object);
          }
        }
        compiled = true;
      }
    }
  }
}
