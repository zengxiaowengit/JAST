package org.qiuer.ast.pattern;


import org.qiuer.ast.Node;

import java.util.List;

public class ObjectPattern implements Pattern {
  String type = "ObjectPattern";
  //[ { key: Literal | Identifier, value: Pattern } ];
  List<Node> properties;
}