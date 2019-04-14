package org.qiuer.ast;

import org.qiuer.ast.statement.IStatement;
import org.qiuer.ast.statement.Statement;
import org.qiuer.core.Context;
import org.qiuer.exception.Const;
import org.qiuer.exception.EReturn;
import org.qiuer.exception.IException;
import org.qiuer.util.JsonUtil;

import java.util.List;

public class Program extends Node {
  protected String type = "Program";
  protected List<IStatement> body;

  @Override
  public void compile() throws IException {

  }

  @Override
  public Object run(Context context) throws EReturn {
    try {
      context.enterBlock();
      for (IStatement statement : body){
        statement.run(context);
      }
      System.out.println(JsonUtil.toPrettyJson(context));
    } catch (IException e1) {
      throw new EReturn(e1.getCode(), e1.getMsg(), null);
    } catch (Exception e2) {
      e2.printStackTrace();
      throw new EReturn(Const.EXCEPTION.UNKNOWN_ERROR, e2.getMessage(), null);
    }finally {
      context.exitBlock();
    }
    return null;
  }
}