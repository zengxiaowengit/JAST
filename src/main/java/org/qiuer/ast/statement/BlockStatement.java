package org.qiuer.ast.statement;

import org.qiuer.core.Context;
import org.qiuer.exception.*;

import java.util.ArrayList;
import java.util.List;

public class BlockStatement extends Statement {
  public String type = "BlockStatement";
  public List<IStatement> body;


  public boolean needScope = true; //单独的block需要新的scope。函数调用等某些场景不需要。可以优化效率。

  @Override
  public void compile() throws IException {
    if (body == null)
      body = new ArrayList<>();
  }

  @Override
  public Object run(Context context) throws IException {
    if(needScope)
      context.enterScope();

    for (IStatement statement : body) {
      statement.run(context);
    }

    if (needScope)
      context.exitScope();
    return null;
  }
}