package org.qiuer.exception;

import java.util.List;
import java.util.Map;

public final class EValidate {

  public static <T> T cast(Object object, Class<? extends T> clazz) throws ERuntime {
    if(clazz.isAssignableFrom(object.getClass())){//子类放后边。
      return (T) object;
    }else {
      throw new ERuntime(Const.EXCEPTION.TYPE_CAST_ERROR, "不能将类型"
              + object.getClass().getSimpleName() + "转换为" + clazz.getSimpleName());
    }
  }

  public static void notNull(Object object) throws ERuntime {
    if (object == null){
      throw new ERuntime(Const.EXCEPTION.CANNOT_BE_NULL,"不能为NULL");
    }
  }

  public static void notEmpty(Object object) throws ERuntime {
    notNull(object);
    if (object instanceof Map && ((Map) object).size() < 1){
      throw new ERuntime(Const.EXCEPTION.CANNOT_BE_EMPTY,"Map不能为空");
    }else if(object instanceof List && ((List) object).size() < 1){
      throw new ERuntime(Const.EXCEPTION.CANNOT_BE_EMPTY,"List不能为空");
    }else if(object instanceof String && ((String) object).trim().equals("")){
      throw new ERuntime(Const.EXCEPTION.CANNOT_BE_EMPTY,"String不能为空");
    }
  }

  public static void assertTrue(boolean flag, String msg) throws ERuntime {
    if (!flag){
      throw new ERuntime(Const.EXCEPTION.UNKNOWN_ERROR,msg);
    }
  }

  public static void assertType(Object object, Class clazz, String msg) throws ERuntime {
    if (!clazz.isAssignableFrom(object.getClass())){
      throw new ERuntime(Const.EXCEPTION.TYPE_CAST_ERROR,msg);
    }
  }
}
