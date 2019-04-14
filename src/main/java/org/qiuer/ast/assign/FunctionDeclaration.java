package org.qiuer.ast.assign;

import org.qiuer.ast.expression.function.ArrowFunctionExpression;
import org.qiuer.ast.expression.function.Function;
import org.qiuer.core.Context;
import org.qiuer.exception.IException;

public class FunctionDeclaration extends Function implements IDeclaration {
  public String type = "FunctionDeclaration";

  @Override
  public Object run(Context context) throws IException {
    Function function =  new ArrowFunctionExpression();
    function.id = this.id;
    function.params = this.params;
    function.body = this.body;
    function.compile();
    context.declareFunction(id.name, function);
    return null;
  }
}