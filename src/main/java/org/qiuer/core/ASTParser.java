package org.qiuer.core;

import org.qiuer.ast.INode;
import org.qiuer.ast.Program;
import org.qiuer.util.JsonUtil;
import org.reflections.Reflections;

import java.lang.reflect.Field;
import java.util.*;

/**
 * json转化为抽象语法树（Abstract Syntax Tree）
 */
public class ASTParser {
  //反序列化的java类型映射。
  private static HashMap<String, Class> typeMapping = new HashMap<String, Class>();

  static {
    initTypeMapping();
  }

  private static void initTypeMapping() {
    Reflections reflections = new Reflections(INode.class.getPackage().getName());//不写包名也可以，但会扫描所有的包，初次加载很慢。
    Set<Class<? extends INode>> registerClasses = reflections.getSubTypesOf(INode.class);
    for (Class<? extends INode> clazz : registerClasses) {
      typeMapping.put(clazz.getSimpleName(), clazz);
    }
  }

  public static Program parse(Map<String, Object> tree) {
    Object ret = generate(tree);
    return ((org.qiuer.ast.File) ret).program;
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
      System.out.println("没有type字段的节点：" + JsonUtil.toPrettyJson(tree));
      return null;
    }
    Class<?> clazz = typeMapping.get(type);
    if(clazz == null){
      throw new RuntimeException("不支持的type:" + type);
    }
    List<Field> fields = new ArrayList<>();
    // 这个是父类的字段。interface 字段是final，不能设值。
    fields.addAll(Arrays.asList(clazz.getFields()));
    fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
    INode node = null;

    try {
      node = (INode) clazz.newInstance();
      for (Map.Entry<String, Object> entry : tree.entrySet()) {
        String key = entry.getKey();
        Object value = entry.getValue();
        //获取key 对应的field。
        Field field = getField(fields, key);
        if (field == null) {
          System.out.println("未在类型" + type + "中找到字段：" + key);
          continue;
        }
        Object element = null;//设置到节点的值。
        if (value instanceof Map) {
          if (((Map) value).containsKey("type")) {
            element = generate((Map<String, Object>) value);
          }
        } else if (value instanceof List) {
          element = new ArrayList<>();
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
            field.set(node, element);
          } catch (IllegalAccessException e) {
            System.out.println("为类型：" + type + "的字段：" + key + "设置为: " + JsonUtil.toPrettyJson(element) + "报错");
            e.printStackTrace();
          }
        }
      }
      node.compile();
    } catch (Exception e) {
      System.out.println("这个节点初始化错误：" + JsonUtil.toPrettyJson(tree));
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
