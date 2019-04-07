package org.qiuer.core;

import org.qiuer.ast.assign.AssignKind;

/**
 * 主要用于保存声明变量的类型，判断是否可以修改等。和runtime的分开保存的。为什么要分离呢？方便做json序列化。后边还有map/array 的function声明也是如此。
 */
public class AssignContext extends AbstractRuntimeContext<String, AssignKind> {

  //是否可修改。
  public boolean isModifiable(String key){
    //TODO 还未实现呢。
    return true;
  }

}