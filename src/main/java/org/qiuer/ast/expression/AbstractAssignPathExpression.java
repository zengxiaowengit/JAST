package org.qiuer.ast.expression;

import org.qiuer.core.Context;
import org.qiuer.exception.Const;
import org.qiuer.exception.ERuntime;
import org.qiuer.exception.IException;

import java.util.List;
import java.util.Map;

/**
 * 可以是一个在assignment path上的表达式。
 */
public abstract class AbstractAssignPathExpression implements Expression{

  /**
   * 获取成员的key。比如：object.data[0].name,返回[object, data, 0, name]
   * @return
   */
  public abstract void addMemberPath(List<Object> path) throws IException;

  /**
   * 根据memberKeys更新值。
   * @param path
   * @param newVal
   * @return
   */
  protected void updateValue(Context context, List<Object> path, Object newVal) throws IException {
    int size = path == null? 0 :path.size();
    int lastIndex = size - 1;
    if(size > 0){
      Object object = context.get(path.get(0).toString());
      for (int i = 1; i < size; i++) {
        Object key = path.get(i);
        if (object == null){
          throw new ERuntime(Const.EXCEPTION.FRAME.UNSUPPORTED_EXPRESSION, "不能为null对象设置属性：" + key);
        }

        if(object instanceof Map){
          if(i == size - 1){//最后一个property，更新对象的值。
            ((Map) object).put(key.toString(), newVal);
            return;
          }
          object = ((Map) object).get(key);
        }else if(object instanceof List){
          int index = Integer.parseInt(key.toString());
          if(i == size - 1){//最后一个property，更新对象的值。
            ((List) object).add(index, newVal);
            return;
          }
          object = ((List) object).get(index);
        }else {// 有可能是java对象，要直接设置属性。后续考虑吧。反射通过field | method设置值。
          throw new ERuntime(Const.EXCEPTION.FRAME.UNSUPPORTED_EXPRESSION, "不支持为除了map和list以外的对象设置值：" + key);
        }
      }
    }
  }

  protected Object getValue(Context context, List<Object> path) throws IException {
    int size = path == null? 0 :path.size();
    int lastIndex = size - 1;
    if(size > 0){
      Object object = context.get(path.get(0).toString());
      for (int i = 1; i < size; i++) {
        Object key = path.get(i);
        if (object == null){
          throw new ERuntime(Const.EXCEPTION.FRAME.UNSUPPORTED_EXPRESSION, "不能为null对象设置属性：" + key);
        }

        if(object instanceof Map){
          if(i == size - 1){
            return ((Map) object).get(key.toString());
          }
          object = ((Map) object).get(key);
        }else if(object instanceof List){
          int index = Integer.parseInt(key.toString());
          if(i == size - 1){//最后一个property，更新对象的值。
            return ((List) object).get(index);
          }
          object = ((List) object).get(index);
        }else {// 有可能是java对象，要直接设置属性。后续考虑吧。反射通过field | method设置值。
          throw new ERuntime(Const.EXCEPTION.FRAME.UNSUPPORTED_EXPRESSION, "不支持为除了map和list以外的对象设置值：" + key);
        }
      }
    }
    return null;
  }
}
