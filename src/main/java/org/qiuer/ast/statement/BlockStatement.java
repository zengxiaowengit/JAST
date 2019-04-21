package org.qiuer.ast.statement;

import org.qiuer.core.Context;
import org.qiuer.exception.*;

import java.util.ArrayList;
import java.util.List;

public class BlockStatement extends Statement {
  public String type = "BlockStatement";
  public List<IStatement> body;

  @Override
  public void compile() throws IException {
    if (body == null)
      body = new ArrayList<>();
  }

  @Override
  public Object run(Context context) throws IException {
    context.enterBlock();
    try {
      for (IStatement statement : body) {
        statement.run(context);
      }
    }catch (IException e){//EReturn 由CallExpression catch。
      throw e;
    }catch (Exception e){
      throw new ERuntime(Const.EXCEPTION.UNKNOWN_ERROR, e.getMessage());
    }finally {
      context.exitBlock();
    }
    return null;
  }
}