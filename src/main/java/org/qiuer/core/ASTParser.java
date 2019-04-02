package org.qiuer.core;

import org.qiuer.ast.Node;
import org.qiuer.ast.Program;
import org.qiuer.util.JsonUtil;

import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.*;

public class ASTParser {
  private static HashMap<String, Class> typeMapping = new HashMap<String, Class>();
  private static URL classUrl = ASTParser.class.getResource("/");

  static {
    String astPath = classUrl.getPath() + "org/qiuer/ast";
    String packageName = "org.qiuer.ast";
    getClasses(packageName, astPath, true, typeMapping);
  }

  private static void getClasses(String packageName, String astPath, final boolean recursive, HashMap<String, Class> classes) {
    File dir = new File(astPath);
    if (!dir.exists() || !dir.isDirectory()) {
      return;
    }
    File[] dirfiles = dir.listFiles(new FileFilter() {
      public boolean accept(File file) {
        return (recursive && file.isDirectory()) || (file.getName().endsWith(".class"));
      }
    });
    for (File file : dirfiles) {
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
            System.out.println("类型：" + type + "，字段：" + key);
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
