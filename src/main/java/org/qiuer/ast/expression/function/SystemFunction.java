package org.qiuer.ast.expression.function;

public abstract class SystemFunction extends Function {
  /**
   * 注册到某个类型上，通过成员函数的方式访问。
   * 注意：不通过成员函数访问的，返回null。
   * @return
   */
  public abstract Class registerToOrNull();

  /**
   * 是否允许属性调用。如：list.length() 简化为 list.length调用。
   * 注：如果变量和函数重名，变量的优先级是高于该函数的优先级的。虽然不推荐存在这样的代码。
   * 除了ECMA Script支持的默认属性，不推荐设置为true。一般应该设置为false。
   * @return
   */
  public abstract boolean allowPropCall();

}
