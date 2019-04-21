

# TODO 

AST的主要结构
- Node
    - Declaration
    - Pattern
    - Statement
    - Expression
        - Function

> expression 和 function才有run，
declaration和pattern、Node不应该有run方法。

> 函数：
全局的函数（数组的filter、push等。）和重写过、自定义到变量上的函数。
定义map保存，全局和localmap。和context原理一样。和block有关系

> 暂时禁止使用this。不好实现，容易出bug。再考虑

TODO 

array 一百万追加。qscript 560ms. (AST 788ms)
map 一百万put。qscript 617ms (AST 487ms)
斐波那契，递归。算到25，qscript 3613ms(AST 1285ms)
空for循环一百万次，qscript 32ms(AST 237ms)
