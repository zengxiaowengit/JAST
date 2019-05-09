package org.qiuer.ast.expression;

import org.apache.commons.lang.StringUtils;
import org.qiuer.ast.expression.function.Function;
import org.qiuer.ast.expression.function.SystemFunction;
import org.qiuer.ast.expression.literal.Literal;
import org.qiuer.ast.expression.literal.NumericLiteral;
import org.qiuer.core.Context;
import org.qiuer.exception.Const;
import org.qiuer.exception.ERuntime;
import org.qiuer.exception.EValidate;
import org.qiuer.exception.IException;

import javax.net.ssl.KeyManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MemberExpression extends Expression{
  public String type = "MemberExpression";
  public IExpression object;
  // Identifier | Expression
  public IExpression property;
  public boolean computed;

  private AccessMode mode;
  @Override
  public void compile() throws IException {
    EValidate.notEmpty(object);
    EValidate.notEmpty(property);
    if(object instanceof Identifier && property instanceof Identifier){
      mode = new IdentifierMode((Identifier)object, (Identifier)property);
    }else if(object instanceof MemberExpression && property instanceof Identifier){
      mode = new MemberIdentifierMode((MemberExpression)object, (Identifier)property);
    }else if(object instanceof Identifier && property instanceof Literal){
      mode = new LiteralMode((Identifier)object, (Literal)property);
    }else if(object instanceof MemberExpression && property instanceof Literal){
      mode = new MemberLiteralMode((MemberExpression)object, (Literal)property);
    }else if(property instanceof Identifier){
      mode = new ExpressionIdentifierMode(object, (Identifier)property);
    }else throw new ERuntime(Const.EXCEPTION.UNSUPPORTED_EXPRESSION,
            "不支持的类型访问：" + object.getClass().getSimpleName() + ", " + property.getClass().getSimpleName());
  }

  @Override
  public Object run(Context context) throws IException {
    Object ret = mode.getValue(context);
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

  /**
   * 获取成员函数
   * @param context
   * @throws IException
   */
  public Function getFunction(Context context) throws IException {
    List<String> path = new ArrayList<>();
    Object o = this.object.run(context);
    //根据类型去找全局定义的函数。
    Function ret = context.getFunction(o.getClass(), EValidate.cast(this.property, Identifier.class).name);
    if(ret != null) return ret;
    this.mode.addMemberPath(path);
    //根据path找自定义函数
    return context.getFunction(StringUtils.join(path, "."));
  }

  protected Object updateVariableValue(Context context, Object newVal) throws IException {
    return this.mode.setValue(context, newVal);
  }

  public void addMemberPath(List<String> path) throws IException {
    this.mode.addMemberPath(path);
  }

  /**
   *
   * @param <L> L 可能是标识符或者member
   * @param <R> R 可能是标识符或者NumberLiteral
   */
  protected abstract class AccessMode<L, R>{
    L object;
    R property;

    AccessMode(L object, R right){
      this.object = object;
      this.property = right;
    }
    // 获取值
    public abstract Object getValue(Context context) throws IException;
    // 设置值
    public abstract Object setValue(Context context, Object newValue) throws IException;

    // 获取成员的key。比如：object.data[0].name,返回[object, data, 0, name]
    public abstract void addMemberPath(List<String> path) throws IException;

    protected Object getProperty(Object object, String key, Context context) throws IException {
      Object ret = null;
      if (object instanceof List) {
        try {
          int index = Integer.valueOf(key);
          ret = ((List) object).get(index);
        }catch (Exception ignored){} // 如：数组.length 是函数，忽略报错。
      }else if(object instanceof Map){
        ret = ((Map) object).get(key);
      } else {
        throw new ERuntime(Const.EXCEPTION.UNSUPPORTED_EXPRESSION, "不支持为除了map和list以外的对象（以后可能会支持java对象）获取属性：" + key);
      }
      if (ret != null) return ret;
      else return getFunction(context);
    }

    protected Object setProperty(Object object, String key, Object value) throws ERuntime {
      if (object instanceof List) {
        int index = Integer.valueOf(key);
        return ((List) object).set(index, value);
      }else if(object instanceof Map){
        return ((Map) object).put(key, value);
      } else {
        throw new ERuntime(Const.EXCEPTION.UNSUPPORTED_EXPRESSION, "不支持为除了map和list以外的对象（以后可能会支持java对象）获取值：" + key);
      }
    }
  }

  protected class IdentifierMode extends AccessMode<Identifier, Identifier> {

    IdentifierMode(Identifier object, Identifier right) {
      super(object, right);
    }

    @Override
    public Object getValue(Context context) throws IException {
      String key = this.object.name;
      Object object = context.getVariableValue(key);
      if (object == null) {
        throw new ERuntime(Const.EXCEPTION.UNSUPPORTED_EXPRESSION, "变量" + key + "值为空，无属性：" + property.name);
      }

      return getProperty(object, property.name, context);
    }

    @Override
    public Object setValue(Context context, Object newValue) throws IException {
      String key = this.object.name;
      Object object = context.getVariableValue(key);
      if (object == null) {
        throw new ERuntime(Const.EXCEPTION.UNSUPPORTED_EXPRESSION, "变量" + key + "值为空，无属性：" + property.name);
      }

      return setProperty(object, property.name, newValue);
    }

    @Override
    public void addMemberPath(List<String> path) throws IException {
      path.add(object.name);
      path.add(property.name);
    }
  }

  protected class MemberIdentifierMode extends AccessMode<MemberExpression, Identifier> {

    MemberIdentifierMode(MemberExpression object, Identifier right) {
      super(object, right);
    }

    @Override
    public Object getValue(Context context) throws IException {
      Object object = this.object.run(context);
      if (object == null) {
        throw new ERuntime(Const.EXCEPTION.UNSUPPORTED_EXPRESSION, "对象值为空，无属性：" + property.name);
      }

      return getProperty(object, property.name, context);
    }

    @Override
    public Object setValue(Context context, Object newValue) throws IException {
      Object object = this.object.run(context);
      if (object == null) {
        throw new ERuntime(Const.EXCEPTION.UNSUPPORTED_EXPRESSION, "对象值为空，无法设置属性：" + property.name);
      }
      return setProperty(object, property.name, newValue);
    }

    @Override
    public void addMemberPath(List<String> path) throws IException {
      object.addMemberPath(path);
      path.add(property.name);
    }
  }

  protected class LiteralMode extends AccessMode<Identifier, Literal> {

    LiteralMode(Identifier object, Literal right) {
      super(object, right);
    }

    @Override
    public Object getValue(Context context) throws IException {
      String key = this.object.name;
      Object object = context.getVariableValue(key);
      if (object == null) {
        throw new ERuntime(Const.EXCEPTION.UNSUPPORTED_EXPRESSION, "变量" + key + "值为空，无法访问属性：" + property.getValue());
      }

      return getProperty(object, property.getValue().toString(), context);
    }

    @Override
    public Object setValue(Context context, Object newValue) throws IException {
      String key = this.object.name;
      Object object = context.getVariableValue(key);
      if (object == null) {
        throw new ERuntime(Const.EXCEPTION.UNSUPPORTED_EXPRESSION, "变量" + key + "值为空，无法访问属性：" + property.getValue());
      }
      return setProperty(object, property.getValue().toString(), newValue);
    }

    @Override
    public void addMemberPath(List<String> path) {
      path.add(object.name);
      path.add(property.getValue().toString());
    }
  }

  protected class MemberLiteralMode extends AccessMode<MemberExpression, Literal> {

    MemberLiteralMode(MemberExpression object, Literal right) {
      super(object, right);
    }

    @Override
    public Object getValue(Context context) throws IException {
      Object object = this.object.run(context);
      if (object == null) {
        throw new ERuntime(Const.EXCEPTION.UNSUPPORTED_EXPRESSION, "变量值为空，无法访问属性：" + property.getValue());
      }
      return getProperty(object, property.getValue().toString(), context);
    }

    @Override
    public Object setValue(Context context, Object newValue) throws IException {
      Object object = this.object.run(context);
      if (object == null) {
        throw new ERuntime(Const.EXCEPTION.UNSUPPORTED_EXPRESSION, "变量值为空，无法访问属性：" + property.getValue().toString());
      }
      return setProperty(object, property.getValue().toString(), newValue);
    }

    @Override
    public void addMemberPath(List<String> path) throws IException {
      object.addMemberPath(path);
      path.add(property.getValue().toString());
    }
  }

  protected class ExpressionIdentifierMode extends AccessMode<IExpression, Identifier> {

    ExpressionIdentifierMode(IExpression object, Identifier right) {
      super(object, right);
    }

    @Override
    public Object getValue(Context context) throws IException {
      Object object = this.object.run(context);
      if (object == null) {
        throw new ERuntime(Const.EXCEPTION.UNSUPPORTED_EXPRESSION, "对象值为空，无属性：" + property.name);
      }
      return getProperty(object, property.name, context);
    }

    @Override
    public Object setValue(Context context, Object newValue) throws IException {
      Object object = this.object.run(context);
      if (object == null) {
        throw new ERuntime(Const.EXCEPTION.UNSUPPORTED_EXPRESSION, "对象值为空，无法设置属性：" + property.name);
      }
      return setProperty(object, property.name, newValue);
    }

    @Override
    public void addMemberPath(List<String> path) throws IException {
      throw new ERuntime(Const.EXCEPTION.UNSUPPORTED_OPERATION, "执行的表达式不支持获取成员路径");
    }
  }
}
