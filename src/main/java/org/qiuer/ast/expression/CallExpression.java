package org.qiuer.ast.expression;

import org.qiuer.ast.expression.function.Function;
import org.qiuer.ast.expression.function.SystemFunction;
import org.qiuer.core.Context;
import org.qiuer.exception.*;

import java.util.ArrayList;
import java.util.List;

public class CallExpression extends Expression {
  public String type = "CallExpression";
  public IExpression callee;
  public List<IExpression> arguments = new ArrayList<>();//调用参数

  private boolean compiled = false;

  public CallExpression(){}
  public CallExpression(IExpression callee, List<IExpression> arguments){
    this.callee = callee;
    this.arguments = arguments;
  }

  @Override
  public void compile() throws IException {
    EValidate.notNull(callee);
    if (arguments == null)
      arguments = new ArrayList<>();
  }

  @Override
  public Object run(Context context) throws IException {
    context.enterScope();
    try {
      Function function;
      if(callee instanceof MemberExpression) function = ((MemberExpression) callee).getFunction(context);
      else if(callee instanceof Identifier) function = context.getFunction(((Identifier) callee).name);
      else if (callee instanceof Function) function = (Function) callee;
      else throw new ERuntime(Const.EXCEPTION.UNSUPPORTED_OPERATION, "函数调用只支持标识符和成员函数调用");
      EValidate.notNull(function);
      beforeRun(function);
//      EValidate.assertTrue(function.params.size() == arguments.size(), "函数调用参数个数必须和函数定义参数个数相同");

      function.prepareParams(context, arguments);
      //调用函数
      return function.run(context);
    }catch (EReturn eReturn){
      return eReturn.getObject();
    }catch (IException e) {
      throw e;
    } catch (Exception e) {
      e.printStackTrace();
      throw new ERuntime(Const.EXCEPTION.UNKNOWN_ERROR, e.getMessage());
    }finally {
      context.exitScope();
    }
  }

  //系统注册函数，通过成员函数调用时，会把自身作为第一个参数传入。如：data.push(1)变成push(data, 1)的形式。
  //js只能在运行时编译。只能在第一次运行时做类似的事情。
  private void beforeRun(Function function){
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


/*  private void prepareParams(Context context, Function function, List<IExpression> arguments) throws IException {
    int size = arguments.size();
    // 初始化函数的参数
    List<IPattern> params = function.params;
    for (int i = 0; i < size; i++) {
      IPattern param = params.get(i);
      IExpression argument = arguments.get(i);
      if (param instanceof Identifier) {
        Object value;
        //函数作为参数，只是定义。不做调用。
        if (argument instanceof ArrowFunctionExpression || argument instanceof FunctionExpression) value = argument;
        else value = argument.run(context);
        context.declareVariable(((Identifier) param).name, value, "let");
      } else {
        throw new ERuntime(1000, "函数定义的参数名称只能是简单的标识符Identifier");
      }
    }
  }*/
}
