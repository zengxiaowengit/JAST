package org.qiuer.ast.assign;

import org.qiuer.exception.Const;
import org.qiuer.exception.ERuntime;

//"=" | "+=" | "-=" | "*=" | "/=" | "%="
//        | "<<=" | ">>=" | ">>>="
//        | "|=" | "^=" | "&="
public enum AssignmentOperator {
  EQUAL,
  PLUS_EQUAL,
  MINUS_EQUAL,
  MULTIPLY_EQUAL,
  DIVIDE_EQUAL,
  MOD_EQUAL,
  LESS_EQUAL,
  GREAT_EQUAL
  ;
  public static AssignmentOperator parse(String str) throws ERuntime {
    if (str == null){
      return null;
    }
    switch (str.toLowerCase().trim()){
      case "=": return EQUAL;
      case "+=": return PLUS_EQUAL;
      case "-=": return MINUS_EQUAL;
      case "*=": return MULTIPLY_EQUAL;
      case "/=": return DIVIDE_EQUAL;
      case "%=": return MOD_EQUAL;
      case "<=": return LESS_EQUAL;
      case ">=": return GREAT_EQUAL;
      default: throw new ERuntime(Const.EXCEPTION.FRAME.UNKNOWN_IDENTIFIER, "未识别的标识符:" + str);
    }
  }
}