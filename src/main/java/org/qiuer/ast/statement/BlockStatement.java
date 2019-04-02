package org.qiuer.ast.statement;

import java.util.List;

public class BlockStatement implements Statement {
  String type = "BlockStatement";
  List<Statement> body;
}