
package org.qiuer.ast;

import java.util.List;

public class VariableDeclaration implements Declaration {

    String type = "VariableDeclaration";
    List<VariableDeclarator> declarations;
    // "var" | "let" | "const";
    String kind;
}