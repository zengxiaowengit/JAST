package org.qiuer.ast.assign;

import org.qiuer.ast.expression.function.CustomFunction;
import org.qiuer.ast.expression.function.Function;
import org.qiuer.core.Context;
import org.qiuer.exception.EValidate;
import org.qiuer.exception.IException;

public class FunctionDeclaration extends Function implements IDeclaration {
  public String type = "FunctionDeclaration";

  @Override
  public void compile() throws IException {
    EValidate.notNull(id);
    EValidate.notNull(params);
    EValidate.notNull(body);
  }

  @Override
  public Object run(Context context) throws IException {
    Function function =  new CustomFunction();
    function.id = this.id;
    function.params = this.params;
    function.body = this.body;
    function.compile();
    context.declareFunction(id.name, function);
    return null;
  }
}