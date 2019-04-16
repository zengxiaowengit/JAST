try {
  let babel = require('babel-core');//babel核心库
  let types = require('babel-types');
  let fs = require('fs');
  let code = fs.readFileSync('../src/main/resources/code.js', 'utf8');
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
  let json = JSON.stringify(ast, null, '  ');
  fs.writeFile('../src/main/resources/ast.json', json , function (err) {
    if (err) {
      console.error(err);
    }
  })
}catch (e) {
  console.log(e);
  throw e;
}