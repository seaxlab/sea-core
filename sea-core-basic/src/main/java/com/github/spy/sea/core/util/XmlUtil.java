package com.github.spy.sea.core.util;

import lombok.extern.slf4j.Slf4j;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * xml
 *
 * @author spy
 * @version 1.0 2019-08-03
 * @since 1.0
 */
@Slf4j
public final class XmlUtil {

    // 1) DOM parser loads whole XML documents in memory while SAX only loads a small part of the XML file in memory.
    //2) DOM parser is faster than SAX because it accesses the whole XML document in memory.
    //3) SAX parser in Java is better suitable for a large XML file than DOM Parser because it doesn't require much memory.
    //4) DOM parser works on Document Object Model while SAX is an event based XML parser.

    /**
     * 读取xml file
     *
     * @param filePath
     * @return
     */
    public static Element getRoot(String filePath) {
        SAXReader reader = new SAXReader();
        Element root = null;
        try {
            // 读取xml的配置文件名，并获取其里面的节点
            root = reader.read(new File(filePath)).getRootElement();
        } catch (Exception e) {
            log.error("fail to read xml", e);
        }
        return root;
    }

    /**
     * Element转list
     *
     * @param root  ontnull
     * @param clazz ontnull
     * @param <T>   ontnull
     * @return bean
     */
    public static <T> List<T> getList(Element root, Class<T> clazz) {
        List<T> list = new ArrayList<>();
        try {
            List<Element> elements = root.elements();
            for (int i = 0; i < elements.size(); i++) {
                T t = getBean(elements.get(i), clazz);
                list.add(t);
            }
        } catch (Exception e) {
            log.error("fail to get list from xml", e);
        }
        return list;
    }

    /**
     * Element转Bean
     *
     * @param root  ontnull
     * @param clazz ontnull
     * @param <T>   ontnull
     * @return bean
     */
    public static <T> T getBean(Element root, Class<T> clazz) {
        try {
            T obj = clazz.newInstance();
            Field[] fields = clazz.getDeclaredFields();
            Method setMethod;
            String fieldType;
            String fieldGenericType;
            String className;
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                fieldType = (field.getType() + "");
                setMethod = obj.getClass().getMethod(
                        "set" + fields[i].getName().substring(0, 1).toUpperCase()
                                + fields[i].getName().substring(1), fields[i].getType());
                if ("interface java.util.List".equals(fieldType)) {
                    fieldGenericType = fields[i].getGenericType() + "";
                    String[] sp1 = fieldGenericType.split("<");
                    String[] sp2 = sp1[1].split(">");
                    className = sp2[0];
                    Object listNode = getList(root.element(fields[i].getName()), Class.forName(className));
                    setMethod.invoke(obj, listNode);
                    continue;
                }

                // check basic
                String text = root.elementText(field.getName());
                if (field.getType().equals(String.class)) {
                    setMethod.invoke(obj, text);
                } else if (field.getType().equals(Integer.class) || field.getType().equals(int.class)) {
                    setMethod.invoke(obj, Integer.parseInt(text));
                } else if (field.getType().equals(Long.class) || field.getType().equals(long.class)) {
                    setMethod.invoke(obj, Long.parseLong(text));
                } else if (field.getType().equals(Double.class) || field.getType().equals(double.class)) {
                    setMethod.invoke(obj, Double.parseDouble(text));
                } else if (field.getType().equals(Float.class) || field.getType().equals(float.class)) {
                    setMethod.invoke(obj, Float.parseFloat(text));
                } else if (field.getType().equals(Byte.class) || field.getType().equals(Byte.class)) {
                    setMethod.invoke(obj, Byte.parseByte(text));
                } else if (field.getType().equals(Boolean.class) || field.getType().equals(boolean.class)) {
                    setMethod.invoke(obj, Boolean.valueOf(text));
                } else if (field.getType().equals(Date.class)) {
                    //TODO
                    setMethod.invoke(obj, Date.parse(text));
                } else {
                    // check class
                    className = field.getType().getName();
                    Object fieldValue = getBean(root.element(field.getName()), Class.forName(className));
                    setMethod.invoke(obj, fieldValue);
                }
            }
            return obj;
        } catch (Exception e) {
            log.error("fail to convert xml to bean", e);
        }
        return null;
    }

    /**
     * 判断是否是合法的list
     */
    public static boolean isList(Element root) {
        int type = 0;
        if (root != null) {
            List<Element> elements = root.elements();
            String elementName;
            String elementNameFlag;
            if (elements != null && elements.size() > 0) {
                elementNameFlag = elements.get(0).getName();
                for (int i = 1; i < elements.size(); i++) {
                    elementName = elements.get(i).getName();
                    if (elementNameFlag.equals(elementName)) {
                        // 是list
                        type = 1;
                    } else {
                        if (type == 1) {
                            log.error("This XML is not in the right format, please add a parent node for Node of the same name!");
                        } else {
                            elementNameFlag = elementName;
                        }
                    }
                }
            }
        }
        if (type == 1) {
            return true;
        } else {
            return false;
        }
    }
}
