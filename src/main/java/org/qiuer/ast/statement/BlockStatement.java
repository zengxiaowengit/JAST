package org.qiuer.ast.statement;

import org.qiuer.core.Context;
import org.qiuer.exception.Const;
import org.qiuer.exception.EReturn;
import org.qiuer.exception.ERuntime;
import org.qiuer.exception.IException;

import java.util.List;

public class BlockStatement extends Statement {
  String type = "BlockStatement";
  List<IStatement> body;

  @Override
  public void compile() throws IException {

  }

  @Override
  public Object run(Context context) throws IException {
    context.enterBlock();
    try {
      for (IStatement statement : body) {
        statement.run(context);
      }
    }catch (EReturn eReturn){
      return eReturn.getObject();
    }catch (IException e){
      throw e;
    }catch (Exception e){
      throw new ERuntime(Const.EXCEPTION.UNKNOWN_ERROR, e.getMessage());
    }finally {
      context.exitBlock();
    }
    return null;
  }
}