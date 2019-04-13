package org.qiuer.ast.pattern;


import org.qiuer.ast.Node;
import org.qiuer.core.Context;
import org.qiuer.exception.IException;

import java.util.List;

public class ObjectPattern extends Pattern {
  String type = "ObjectPattern";
  //[ { key: Literal | Identifier, value: Pattern } ];
  List<Node> properties;

  @Override
  public void compile() throws IException {

  }

  @Override
  public Object run(Context context) throws IException {
    return null;
  }
}