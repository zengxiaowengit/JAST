/**
 * Copyright 2019 bejson.com
 */
package org.qiuer.ast;

import org.qiuer.ast.expression.Expression;
import org.qiuer.ast.pattern.Pattern;

public class VariableDeclarator implements Node {
    String type = "VariableDeclarator";
    Pattern id;
    Expression init;
}