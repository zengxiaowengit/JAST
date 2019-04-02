package org.qiuer.ast;

public interface Node {
  String type = "Node";
  SourceLocation loc = null;
  int start = 0;
  int end = 0;
}