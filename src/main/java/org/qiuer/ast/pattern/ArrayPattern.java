package org.qiuer.ast.pattern;


import org.qiuer.ast.Node;

import java.util.List;

public class ArrayPattern implements Pattern {
  String type = "ArrayPattern";
  //[ Pattern | null ];
  List<Node> elements;
}