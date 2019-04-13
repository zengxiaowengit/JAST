package org.qiuer.ast.assign;

import org.qiuer.exception.Const;
import org.qiuer.exception.ERuntime;

public enum AssignKind {
  LET, VAR, CONST;

  public static AssignKind parse(String str) throws ERuntime {
    if (str == null){
      return null;
    }
    switch (str.toLowerCase().trim()){
      case "let": return LET;
      case "var": return VAR;
      case "const": return CONST;
      default:throw new ERuntime(Const.EXCEPTION.UNKNOWN_IDENTIFIER, "未识别的标识符:" + str);
    }
  }
}
