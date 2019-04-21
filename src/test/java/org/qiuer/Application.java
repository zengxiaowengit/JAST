package org.qiuer;


import org.qiuer.ast.Program;
import org.qiuer.core.ASTParser;
import org.qiuer.core.ASTRunner;
import org.qiuer.util.JsonUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Application {

  public static void main(String[] args) throws IOException, Exception {
    InputStream stream = ASTParser.class.getResourceAsStream("/ast.json");
    String encoding = "UTF-8";
    BufferedReader reader = new BufferedReader(new InputStreamReader(stream, encoding));
    StringBuilder sb = new StringBuilder();
    String line = "";
    while ((line = reader.readLine()) != null) {
      sb.append(line);
    }
    Map<String, Object> json = JsonUtil.toMap(sb.toString());


    Program program = ASTParser.parse(json);
    long start = System.currentTimeMillis();
    Object ret = ASTRunner.run(program);

    System.out.println("=======================Return==========================");
    System.out.println("耗时(ms)：" + (System.currentTimeMillis() - start));
    System.out.println(JsonUtil.toPrettyJson(ret));

    System.out.println("=======================================================");
    start = System.currentTimeMillis();
    List<Integer> a = new ArrayList<>();
    for (int i = 0; i < 1000000; i++){
      a.add(i);
    }
    System.out.println("耗时(ms)：" + (System.currentTimeMillis() - start));


  }
}
