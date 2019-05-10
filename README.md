
# es6-in-java

#### 介绍
在java中运行ECMA Script 6。 多么酷的一件事情！
前端也能无缝接入开发后端，享受java的集群和扩容的能力！
运行速度爆表，使用JIT技术，预编译技术优化性能，追求极致！
前后端统一语言，降低开发入门门槛，我们在路上！

###### 市场现状
java如何运行JavaScript？我们有以下几种方法：
- 亚马逊的的rhino。目前已经不怎么维护了，只支持很旧的javascript版本。而且速度很慢，无对应学习文档。
- java 自带的NashornScript Engine。不支持ES6，支持旧版。速度慢。
- google的aviator表达式引擎。自己基于表达式引擎去实现。不支持js语法，开发难度大。

###### es6-in-java


###### 为什么不是Django？不是Node.js？
选用java，看重的是java的社区环境和良好的横向扩展性，集群能力，以及强大的库环境。比如spring社区。
再比如，我们想做一个OA的流程引擎系统，想使用es6的语法，又想使用java的activiti，那很简单，有了我们的项目，
你只需熟悉我们封装好的流程引擎接口，使用es6语法也能开发。

这是Django、Node.js不具备的能力。获取他们能简单操作数据库，做简单的业务，但是在中间件和社区沉淀方面，
差了java好几个层次。

#### 软件架构

##### es6解析为抽象语法树

##### java中解析语法树

##### java中运行语法树


AST的主要结构
- Node
    - Declaration
    - Pattern
    - Statement
    - Expression
        - Function


#### es-in-java和aviator性能测试对比
| 对比项目                  |es6-in-java    |aviator        |java       |    说明
| ------                    |------        |------          |------     |-----
| 1.array一百万次push       |408ms          |448ms          |52ms       |
| 2.map 一百万次put         |268ms          |386ms          |20ms       |
| 3.空for循环一百万次       |77ms           |30ms           |2ms         |for循环效率有待改进。
| 4.一百万次赋值            |161ms          |325ms          |6ms        |
| 5.斐波那契函数调用，算到25 |1285ms         |3345ms         |2ms        |
| 6.获取时间函数调用100万次  |225-328ms      |304-366ms      |10ms       |
| 7.加减乘除100万次          |548-561ms      |1544ms         |           |

##### 运行代码示例：简单的斐波那契数列

```ecmascript 6
function Fib (n) {
  if ( n <= 1 ) {
    return 1;
  }
  return Fib(n - 1) + Fib(n - 2);
}

let result = Fib(25);

```

#### 安装教程

1. xxxx
2. xxxx
3. xxxx

#### 使用说明

1. xxxx
2. xxxx
3. xxxx

#### 参与贡献

1. Fork 本仓库
2. 新建 Feat_xxx 分支
3. 提交代码
4. 新建 Pull Request


#### 码云特技

1. 使用 Readme\_XXX.md 来支持不同的语言，例如 Readme\_en.md, Readme\_zh.md
2. 码云官方博客 [blog.gitee.com](https://blog.gitee.com)
3. 你可以 [https://gitee.com/explore](https://gitee.com/explore) 这个地址来了解码云上的优秀开源项目
4. [GVP](https://gitee.com/gvp) 全称是码云最有价值开源项目，是码云综合评定出的优秀开源项目
5. 码云官方提供的使用手册 [https://gitee.com/help](https://gitee.com/help)
6. 码云封面人物是一档用来展示码云会员风采的栏目 [https://gitee.com/gitee-stars/](https://gitee.com/gitee-stars/)
