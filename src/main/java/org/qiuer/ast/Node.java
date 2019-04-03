package org.qiuer.ast;

import org.qiuer.core.Context;
import org.qiuer.exception.IException;

public interface Node {
  String type = "Node";
  SourceLocation loc = null;

  Object run(Context context) throws IException;
}