
package org.qiuer.ast.assign;

import org.qiuer.core.Context;
import org.qiuer.exception.IException;

import java.util.List;

public class VariableDeclaration implements Declaration {

    String type = "VariableDeclaration";
    List<VariableDeclarator> declarations;
    // "var" | "let" | "const";
    String kind;

    @Override
    public Object run(Context context) throws IException {
        //TODO 如果是const，需要做点什么。此处暂不考虑。
        for (VariableDeclarator declarator: declarations){
            declarator.run(context);
        }
        return null;
    }
}