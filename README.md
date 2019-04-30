

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

# 性能测试对比
