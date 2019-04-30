package org.qiuer.web;

import org.qiuer.ast.Program;
import org.qiuer.core.ASTParser;
import org.qiuer.core.ASTRunner;
import org.qiuer.util.JsonUtil;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class Action {

  @RequestMapping(value = "/run")
  public Object run(@RequestBody Map<String, Object> ast){
    Program program = ASTParser.parse(ast);
    long start = System.currentTimeMillis();
    Object ret = ASTRunner.run(program);
    System.out.println("=======================Return==========================");
    System.out.println("耗时(ms)：" + (System.currentTimeMillis() - start));
    System.out.println(JsonUtil.toPrettyJson(ret));
    return ret;
  }
}
