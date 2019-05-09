
package org.qiuer.ast.assign;

import org.qiuer.core.Context;
import org.qiuer.exception.IException;

import java.util.List;

public class VariableDeclaration extends Declaration {

  public String type = "VariableDeclaration";
  public List<VariableDeclarator> declarations;
  // "var" | "let" | "const";
  String kind;

  @Override
  public void compile() throws IException {
    AssignKind assignKind;
    if ("const".equals(kind)) {
      assignKind = AssignKind.CONST;
    }else{
      assignKind = AssignKind.LET;
    }

    for (VariableDeclarator declaration : declarations) {
      declaration.assignKind = assignKind;
    }
  }

  @Override
  public Object run(Context context) throws IException {
    for (VariableDeclarator declarator : declarations) {
      declarator.run(context);
    }
    return null;
  }
}