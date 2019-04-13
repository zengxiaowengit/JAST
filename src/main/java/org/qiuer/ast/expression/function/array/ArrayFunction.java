package org.qiuer.ast.expression.function.array;

import org.qiuer.ast.expression.function.Function;

public abstract class ArrayFunction extends Function {



  //TODO 待实现。是否允许属性调用。如：list.length() 简化为 list.length
  public static boolean allowPropCall(){
    return false;
  }
}
