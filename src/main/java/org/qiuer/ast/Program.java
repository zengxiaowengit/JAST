package org.qiuer.ast;

import org.qiuer.ast.statement.IStatement;
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
  public Object run(Context context) throws IException {
    try {
      context.enterBlock();
      //到时候put一些全局变量进去。如：JSON.parse()
      for (IStatement statement : body){
        statement.run(context);
      }
      System.out.println(JsonUtil.toPrettyJson(context));
    }catch (EReturn eReturn){
      return eReturn;
    }catch (IException e1) {
      throw e1;
    } catch (Exception e2) {
      e2.printStackTrace();
      throw new EReturn(Const.EXCEPTION.UNKNOWN_ERROR, e2.getMessage() == null? "未知错误" : e2.getMessage(), null);
    }finally {
      context.exitBlock();
    }
    return null;
  }
}