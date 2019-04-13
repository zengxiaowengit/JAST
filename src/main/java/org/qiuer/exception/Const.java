package org.qiuer.exception;

public interface Const {
  interface EXCEPTION{
    //未知错误
    int UNKNOWN_ERROR = 10;
    //类型转换错误
    int TYPE_CAST_ERROR = 11;
    //调用参数错误
    int WRONG_PARAMS_ON_CALL = 12;
    // 未定义变量
    int UNDEFINED_VARIABLE = 13;
    // 变量不能修改
    int VARIABLE_CANNOT_MODIFIED = 14;
    // 不能为NULL
    int CANNOT_BE_NULL = 15;
    // 不能为空
    int CANNOT_BE_EMPTY = 16;


    //////////////////////以下为框架异常//////////////////////////

    //不支持的表达式和声明
    int UNSUPPORTED_EXPRESSION = 100;
    //不支持的操作
    int UNSUPPORTED_OPERATION = 100;
    //未识别的标识符
    int UNKNOWN_IDENTIFIER = 200;

  }
}
