package org.qiuer.ast.expression;

import org.apache.commons.lang.StringUtils;
import org.qiuer.ast.assign.AssignmentOperator;
import org.qiuer.ast.expression.function.Function;
import org.qiuer.ast.pattern.IPattern;
import org.qiuer.core.Context;
import org.qiuer.exception.Const;
import org.qiuer.exception.ERuntime;
import org.qiuer.exception.EValidate;
import org.qiuer.exception.IException;

import java.util.ArrayList;
import java.util.List;

// 更新值。
public class AssignmentExpression extends Expression{
  public String type = "AssignmentExpression";
  public String operator;
  public IPattern left;
  public IExpression right;

  private AssignMode mode;

  @Override
  public void compile() throws IException {
    EValidate.notNull(operator);
    EValidate.notNull(left);
    EValidate.notNull(right);
    if(left instanceof Identifier && right instanceof Function){
      mode = new IdFunctionAssignMode((Identifier) left,(Function) right);
    }else if(left instanceof Identifier){
      mode = new IdVariableAssignMode((Identifier) left, right);
    }else if(left instanceof MemberExpression && right instanceof Function){
      mode = new MemberFunctionAssignMode((MemberExpression) left,(Function) right);
    }else if(left instanceof MemberExpression){
      mode = new MemberVariableAssignMode((MemberExpression) left, right);
    }else throw new ERuntime(Const.EXCEPTION.UNSUPPORTED_EXPRESSION,
            "不支持的类型声明：" + left.getClass().getSimpleName() + ": " + right.getClass().getSimpleName());
  }

  @Override
  public Object run(Context context) throws IException {
    AssignmentOperator op = AssignmentOperator.parse(operator);
    switch (op){
      case EQUAL:
        mode.assign(context);
    }
    return null;
  }

  protected abstract class AssignMode<L, R>{
    L left;
    R right;

    AssignMode(L left, R right){
      this.left = left;
      this.right = right;
    }

    // 这个不是声明。是更新
    public abstract void assign(Context context)throws IException;
  }

  class IdVariableAssignMode extends AssignMode<Identifier, IExpression>{

    IdVariableAssignMode(Identifier left, IExpression right) {
      super(left, right);
    }

    @Override
    public void assign(Context context) throws IException {
      context.updateVariable(left.name, right.run(context));
    }
  }

  class IdFunctionAssignMode extends AssignMode<Identifier, Function>{

    IdFunctionAssignMode(Identifier left, Function right) {
      super(left, right);
    }

    @Override
    public void assign(Context context) throws IException {
      context.declareFunction(left.name, right);
    }
  }

  class MemberVariableAssignMode extends AssignMode<MemberExpression, IExpression>{

    MemberVariableAssignMode(MemberExpression left, IExpression right) {
      super(left, right);
    }

    @Override
    public void assign(Context context) throws IException {
      this.left.updateVariableValue(context, this.right.run(context));
    }
  }

  class MemberFunctionAssignMode extends AssignMode<MemberExpression, Function>{

    MemberFunctionAssignMode(MemberExpression left, Function right) {
      super(left, right);
    }

    @Override
    public void assign(Context context) throws IException {
      List<String> path = new ArrayList<>();
      this.left.addMemberPath(path);
      context.declareFunction(StringUtils.join(path, "."), this.right);
    }
  }

}
