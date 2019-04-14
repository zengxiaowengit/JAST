package org.qiuer.ast.expression.function;

public abstract class CustomFunction extends Function {

  /**
   * 获取定义的函数名字。
   * @return
   */
  public abstract String getName();

  public abstract Class registerTo();

  /**
   * 是否允许属性调用。如：list.length() 简化为 list.length调用。
   * 注：如果变量和函数重名，变量的优先级是高于该函数的优先级的。虽然不推荐存在这样的代码。
   * 除了ECMA Script支持的默认属性，不推荐设置为true。一般应该设置为false。
   * @return
   */
  public abstract boolean allowPropCall();

}
