package org.qiuer;


import org.qiuer.core.ASTParser;
import org.qiuer.util.JsonUtil;

import java.io.*;
import java.util.Map;

/**
 * let tips = {};
 * tips.code = 0;
 * tips.msg = '成功';
 */
public class Main {

  public static void main(String[] args) throws IOException {
    File file = new File("D:\\github\\AST\\src\\main\\resources\\ast.json");
    FileInputStream stream;
    String encoding = "UTF-8";
    BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), encoding));
    StringBuilder sb = new StringBuilder();
    String line = "";
    while((line = reader.readLine()) != null){
      sb.append(line);
    }
    Map<String, Object> json = JsonUtil.toMap(sb.toString());
    Object parser = new ASTParser().parse(json);

    System.out.println("hello world.");
  }
}
