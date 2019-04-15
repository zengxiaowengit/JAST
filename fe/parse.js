let babel = require('babel-core');//babel核心库
let types = require('babel-types');
let code = `codes.map(code=>{return code.toUpperCase()})`;//转换语句

// let visitor = {
//     ArrowFunctionExpression(path) {//定义需要转换的节点
//         let params = path.node.params
//         let blockStatement = path.node.body
//         let func = types.functionExpression(null, params, blockStatement, false, false)
//         path.replaceWith(func) //
//     }
// }

// let arrayPlugin = { visitor }
let result = babel.transform(code, {
    plugins: [
        // arrayPlugin
    ]
});

let ast = result.ast;
delete ast.tokens;
return ast;
