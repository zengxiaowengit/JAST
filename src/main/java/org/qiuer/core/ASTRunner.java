package org.qiuer.core;

import org.qiuer.ast.Program;
import org.qiuer.exception.EReturn;

public class ASTRunner {

  public static EReturn run(Program program){
    try {
      Context context = new Context();
      program.run(context);
    } catch (EReturn eReturn) {
      return eReturn;
    }
    return null;
  }
}
