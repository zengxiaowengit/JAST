package org.qiuer.exception;

public interface Const {
  interface EXCEPTION{
    //未知错误
    int UNKNOWN_ERROR = 10;
    //类型转换错误
    int TYPE_CAST_ERROR = 11;

    interface FRAME{
      //不支持的表达式和声明
      int UNSUPPORTED_EXPRESSION = 100;

      //不支持的操作
      int UNSUPPORTED_OPERATION = 100;

      //未识别的标识符
      int UNKNOWN_IDENTIFIER = 200;
      // 未定义变量
      int UNDEFINED_VARIABLE = 201;

    }
  }
}
