package org.qiuer.ast;

public abstract class Node implements INode{
  public String type = "Node";
  public SourceLocation loc = null;
  public int start;
  public int end;
}