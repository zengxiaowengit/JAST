package org.qiuer.ast.expression;

import org.qiuer.ast.expression.function.Function;
import org.qiuer.ast.pattern.IPattern;
import org.qiuer.core.Context;
import org.qiuer.exception.*;

import java.util.ArrayList;
import java.util.List;

public class CallExpression extends Expression {
  String type = "CallExpression";
  IExpression callee;
  List<IExpression> arguments = new ArrayList<>();//调用参数

  @Override
  public void compile() throws IException {
    if (callee instanceof MemberExpression) {//函数作为成员函数调用时，第一个参数是调用对象本身。
      arguments.add(0, ((MemberExpression) callee).object);
    }
    // 普通函数定义，是通过Identifier找到函数，然后调用。
  }

  @Override
  public Object run(Context context) throws IException {
    try {
      context.enterBlock();
      Function function = (Function) callee.run(context);
      EValidate.assertTrue(function.params.size() == arguments.size(), "函数调用参数个数必须和函数定义参数个数相同");
      int size = arguments.size();
      // 初始化函数的参数
      for (int i = 0; i < size; i++) {
        IPattern param = function.params.get(i);
        IExpression argument = arguments.get(i);
        if (param instanceof Identifier) {
          context.update(((Identifier) param).name, argument.run(context));
        } else {
          throw new ERuntime(1000, "函数定义的参数名称只能是简单的标识符Identifier");
        }
      }
      //调用函数
      function.run(context);
      return null;
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

}
