/**
  * Copyright 2019 bejson.com 
  */
package org.qiuer.ast;

import org.qiuer.ast.statement.Statement;

import java.util.List;

public class Program implements Node{
  String type = "Program";
  List<Statement>body;
}