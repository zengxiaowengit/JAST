package org.qiuer.ast;

import org.qiuer.core.Context;
import org.qiuer.exception.IException;

public interface INode {

  void compile() throws IException;

  Object run(Context context) throws IException;
}