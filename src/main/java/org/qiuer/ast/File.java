package org.qiuer.ast;

import org.qiuer.core.Context;
import org.qiuer.exception.IException;

public class File extends Node {
  public String type = "File";
  public Program program;

  @Override
  public void compile() throws IException {

  }

  @Override
  public Object run(Context context) throws IException {
    return null;
  }
}
