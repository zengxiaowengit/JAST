package org.qiuer.core;

import org.qiuer.ast.Node;
import org.qiuer.ast.Program;
import org.qiuer.util.JsonUtil;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.*;

/**
 * json转化为抽象语法树（Abstract Syntax Tree）
 */
public class ASTParser {
  //反序列化的java类型映射。
  private static HashMap<String, Class> typeMapping = new HashMap<String, Class>();
  private static URL classPathUrl = ASTParser.class.getResource("/");

  private static String astPath = classPathUrl.getPath() + Node.class.getPackage().getName().replaceAll("\\.", "/");
  private static String astPackage = Node.class.getPackage().getName();

  static {
    getClasses(astPackage, astPath, true, typeMapping);
  }

  private static void getClasses(String packageName, String astPath, final boolean recursive, HashMap<String, Class> classes) {
    File dir = new File(astPath);
    if (!dir.exists() || !dir.isDirectory()) {
      return;
    }
    File[] files = dir.listFiles(file -> (recursive && file.isDirectory()) || (file.getName().endsWith(".class")));
    if (files != null) {
      for (File file : files) {
        if (file.isDirectory()) {
          getClasses(packageName + "." + file.getName(), file.getAbsolutePath(), recursive, classes);
        } else {
          String className = file.getName().substring(0, file.getName().length() - 6);
          try {
            //classes.add(Class.forName(packageName + '.' + className));
            Class clazz = Thread.currentThread().getContextClassLoader().loadClass(packageName + '.' + className);
            classes.put(clazz.getSimpleName(), clazz);
          } catch (ClassNotFoundException e) {
            e.printStackTrace();
          }
        }
      }
    }
  }

  public static Program parse(Map<String, Object> tree) {
    Object ret = generate(tree);
    return (Program) ret;
  }

  /**
   * 反射获取字段，设置属性。
   * 如果值是数组，循环递归，add进list。
   * 如果是对象：没有type属性则返回，有type属性：
   * new 对象，为每个字段设置值。判断值有没有数组。有数组递归。
   *
   * @return
   */
  private static Object generate(Map<String, Object> tree) {
    if (tree == null)
      return null;
    String type = tree.get("type").toString();
    System.out.println(type);
    if (type == null) {
      System.out.println("没有type的节点：" + JsonUtil.toJson(tree));
      return null;
    }
    Class<?> clazz = typeMapping.get(type);
    if(clazz == null){
      throw new RuntimeException("没有为" + type + "找到对应的支持的序列化类型");
    }
    List<Field> fields = new ArrayList<>();
//    fields.addAll(Arrays.asList(clazz.getFields())); 这个是父类的字段。interface 字段是final，不能设值。
    fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
    Node node = null;

    try {
      node = (Node) clazz.newInstance();
      Node finalNode = node;
      tree.forEach((key, value) -> {
        //获取key 对应的field。
        Field field = getField(fields, key);
        if (field == null) {
          System.out.println("未在类型" + type + "中找到字段：" + key);
          return;
        }
        Object element = null;//设置到节点的值。
        if (value instanceof Map) {
          if (((Map) value).containsKey("type")) {
            element = generate((Map<String, Object>) value);
          }
        } else if (value instanceof List) {
          element = new ArrayList<Object>();
          Object finalElement = element;
          ((List) value).forEach(item -> {
            ((ArrayList) finalElement).add(generate((Map<String, Object>) item));
          });
        } else {// 基本数据类型，直接设置。
          element = value;
        }

        //把element通过反射设置到对象里边。
        field.setAccessible(true);
        if (null != element) {
          try {
            field.set(finalNode, element);
          } catch (IllegalAccessException e) {
            System.out.println("为类型：" + type + "的字段：" + key + "设置为: " +  JsonUtil.toJson(element) + "报错");
            e.printStackTrace();
          }
        }

      });
    } catch (Exception e) {
      System.out.println("这个节点初始化错误：" + JsonUtil.toJson(tree));
      e.printStackTrace();
    }
    return node;
  }

  private static Field getField(List<Field> fields, String name) {
    for (Field f : fields) {
      if (f.getName().equals(name)) {
        return f;
      }
    }
    return null;
  }

}
