package org.qiuer.core;

import org.qiuer.ast.Program;
import org.qiuer.exception.EReturn;
import org.qiuer.exception.IException;
import org.springframework.stereotype.Component;

@Component
public class ASTRunner {

  public static Object run(Program program){
    try {
      Context context = new Context();
      return program.run(context);
    } catch (EReturn eReturn) {
      return eReturn;
    } catch (IException e) {
      e.printStackTrace();
    }
    return null;
  }
}
