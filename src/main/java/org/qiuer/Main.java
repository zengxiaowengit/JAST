package org.qiuer;


import org.qiuer.ast.Program;
import org.qiuer.core.ASTParser;
import org.qiuer.util.JsonUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

/**
 * let tips = {};
 * tips.code = 0;
 * tips.msg = '成功';
 */
public class Main {

  public static void main(String[] args) throws IOException {
    InputStream stream = ASTParser.class.getResourceAsStream("/ast.json");
    String encoding = "UTF-8";
    BufferedReader reader = new BufferedReader(new InputStreamReader(stream, encoding));
    StringBuilder sb = new StringBuilder();
    String line = "";
    while((line = reader.readLine()) != null){
      sb.append(line);
    }
    Map<String, Object> json = JsonUtil.toMap(sb.toString());
    Program program = ASTParser.parse(json);

    System.out.println("hello world.");
  }
}
