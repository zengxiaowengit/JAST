package org.qiuer.ast.pattern;

import org.qiuer.ast.Node;

/**
 * 解构。如: let {a,b} = obj; 会吧obj.a 赋值给变量a, obj.b赋值给变量b。
 * 不解构的话，左边就是一个Identifier，直接用name赋值。
 */
public interface Pattern extends Node {
}
