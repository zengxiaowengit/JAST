package org.qiuer.ast.expression;

import org.apache.commons.lang.StringUtils;
import org.qiuer.core.Context;
import org.qiuer.exception.Const;
import org.qiuer.exception.ERuntime;
import org.qiuer.exception.IException;

import java.util.List;
import java.util.Map;

/**
 * 可以是一个在assignment path上的表达式。
 */
public abstract class AbstractAssignPathExpression extends Expression{

  /**
   * 获取成员的key。比如：object.data[0].name,返回[object, data, 0, name]
   * @return
   */
  public abstract void addMemberPath(List<Object> path) throws IException;

  /**
   * 根据memberKeys更新变量值。
   * @param path
   * @param newVal
   * @return
   */
  protected void updateVariableValue(Context context, List<Object> path, Object newVal) throws IException {
    int size = path == null? 0 :path.size();
    if(size == 1){
      context.updateVariable(path.get(0).toString(), newVal);
      return;
    }
    int lastIndex = size - 1;
    if(size > 0){
      Object object = context.getVariableValue(path.get(0).toString());
      for (int i = 1; i < size; i++) {
        Object key = path.get(i);
        if (object == null){
          throw new ERuntime(Const.EXCEPTION.UNSUPPORTED_EXPRESSION, "不能为null对象设置属性：" + key);
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
          throw new ERuntime(Const.EXCEPTION.UNSUPPORTED_EXPRESSION, "不支持为除了map和list以外的对象设置值：" + key);
        }
      }
    }
  }

  protected Object getVariableValue(Context context, List<Object> path) throws IException {
    int size = path == null? 0 :path.size();
    Object ret = null;
    if(size > 0){
      Object object = context.getVariableValue(path.get(0).toString());
      Object lastObject = object; //上一个object的值。
      for (int i = 1; i < size; i++) {
        Object key = path.get(i);
        if (object == null){
          throw new ERuntime(Const.EXCEPTION.UNSUPPORTED_EXPRESSION, "不能为null对象设置属性：" + key);
        }

        if(object instanceof Map){
          lastObject = object;
          if(i == size - 1){
            ret = ((Map) object).get(key.toString());
          }
          object = ((Map) object).get(key);
        }else if(object instanceof List){
          try {
            lastObject = object;
            int index = Integer.parseInt(key.toString());
            if(i == size - 1){//最后一个property，get到值返回。
              ret = ((List) object).get(index);
            }
            object = ((List) object).get(index);
          }catch (Exception ignored){}// parseInt有可能出异常。如list.push(1)，push抛异常

        }else {// 有可能是java对象，要直接设置属性。后续考虑吧。
          throw new ERuntime(Const.EXCEPTION.UNSUPPORTED_EXPRESSION, "不支持为除了map和list以外的对象（以后可能会支持java对象）获取值：" + key);
        }
      }
      if (ret != null) return ret;
      //根据lastObject的类型去找全局定义的函数。
      ret = context.getFunction(lastObject.getClass(), path.get(path.size() - 1).toString());
      if (ret != null) return ret;
      //根据path找自定义函数
      return context.getFunction(StringUtils.join(path, "."));
    }
    return null;
  }
}
