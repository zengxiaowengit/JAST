package org.qiuer.ast;

import org.qiuer.ast.statement.Statement;
import org.qiuer.core.Context;
import org.qiuer.exception.Const;
import org.qiuer.exception.EReturn;
import org.qiuer.exception.EReturnJson;
import org.qiuer.exception.IException;

import java.util.List;

public class Program implements Node {
  protected String type = "Program";
  protected List<Statement> body;

  @Override
  public Object run(Context context) throws EReturn, EReturnJson {
    try {
      for (Statement statement : body){
        statement.run(context);
      }
    } catch (IException e1) {
      throw new EReturnJson(e1.getCode(), e1.getMsg(), null);
    } catch (Exception e2) {
      e2.printStackTrace();
      throw new EReturnJson(Const.EXCEPTION.UNKNOWN_ERROR, e2.getMessage(), null);
    }
    return null;
  }
}