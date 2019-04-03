package org.qiuer.ast.statement;

import org.qiuer.core.Context;
import org.qiuer.exception.IException;

import java.util.List;

public class BlockStatement implements Statement {
  String type = "BlockStatement";
  List<Statement> body;

  @Override
  public Object run(Context context) throws IException {
    return null;
  }
}