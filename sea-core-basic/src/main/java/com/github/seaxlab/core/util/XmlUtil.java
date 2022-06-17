package com.github.seaxlab.core.util;

import com.github.seaxlab.core.exception.Precondition;
import com.google.common.base.MoreObjects;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.bind.*;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.namespace.QName;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
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

    /**
     * object to xml string
     *
     * @param root
     * @return
     */
    public static String toString(Object root) {
        return toString(root, "UTF-8");
    }

    /**
     * Java Object->Xml.
     *
     * @param root
     * @param encoding
     */
    public static String toString(Object root, String encoding) {
        try {
            StringWriter writer = new StringWriter();
            createMarshaller(root.getClass(), encoding).marshal(root, writer);
            return writer.toString();
        } catch (JAXBException e) {
            log.error("fail to marshal", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Java Object->Xml, 特别支持对Root Element是Collection的情形.
     */
    @SuppressWarnings("unchecked")
    public static String toString(Class<?> clazz, Collection<?> root, String rootName, String encoding) {
        try {
            CollectionWrapper wrapper = new CollectionWrapper();
            wrapper.collection = root;

            JAXBElement<CollectionWrapper> wrapperElement = new JAXBElement<>(new QName(rootName), CollectionWrapper.class, wrapper);

            StringWriter writer = new StringWriter();
            createMarshaller(clazz, encoding).marshal(wrapperElement, writer);
            return writer.toString();
        } catch (JAXBException e) {
            log.error("fail to marshal", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Xml->Java Object.
     */
    public static <T> T parse(String xml, Class<T> clazz) {
        try {
            StringReader reader = new StringReader(xml);
            return (T) createUnmarshaller(clazz).unmarshal(reader);
        } catch (JAXBException e) {
            log.error("fail to unmarshal", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Xml->Java Object, 支持大小写敏感或不敏感.
     */
    @SuppressWarnings("unchecked")
    public static <T> T parse(String xml, boolean caseSensitive, Class<T> clazz) {
        try {
            String fromXml = xml;
            if (!caseSensitive) fromXml = xml.toLowerCase();
            StringReader reader = new StringReader(fromXml);
            return (T) createUnmarshaller(clazz).unmarshal(reader);
        } catch (JAXBException e) {
            log.error("fail to unmarshal", e);
            throw new RuntimeException(e);
        }
    }


    /**
     * 封装Root Element 是 Collection的情况.
     */
    public static class CollectionWrapper {
        @SuppressWarnings("unchecked")
        @XmlAnyElement
        protected Collection collection;
    }

    /**
     * 创建Marshaller, 设定encoding(可为Null).
     */
    private static Marshaller createMarshaller(Class clazz, String encoding) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
            Marshaller marshaller = jaxbContext.createMarshaller();

            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE); // 格式化输出
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.FALSE);// 是否省略xml头信息

            if (encoding != null && !encoding.trim().isEmpty()) {
                marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);
            }

            return marshaller;
        } catch (JAXBException e) {
            log.error("fail to create jaxb marshaller", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 创建UnMarshaller.
     */
    private static Unmarshaller createUnmarshaller(Class clazz) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
            return jaxbContext.createUnmarshaller();
        } catch (JAXBException e) {
            log.error("fail to create jaxb unmarshaller", e);
            throw new RuntimeException(e);
        }
    }

    // document

    /**
     * create xml document
     *
     * @return document
     */
    public static Document create() {
        Document document = DocumentHelper.createDocument();
        return document;
    }

    /**
     * create one xml document，带有根节点
     *
     * @param rootNode root node name
     * @return document
     */
    public static Document create(String rootNode) {
        Document document = DocumentHelper.createDocument();
        document.addElement(rootNode);
        return document;
    }

    /**
     * 增加一个节点,如果是根节点只能加一个
     *
     * @param doc      document
     * @param nodeName 节点名称
     * @param value    节点值
     */
    public static Element addElement(Document doc, String nodeName, String value) {
        Precondition.checkNotBlank(nodeName, "xml节点名称不能为空");

        Element node = doc.addElement(nodeName);
        node.setText(MoreObjects.firstNonNull(value, ""));
        return node;
    }

    /**
     * 增加子节点
     *
     * @param element
     * @param nodeName
     * @param value
     */
    public static Element addElement(Element element, String nodeName, String value) {
        Precondition.checkNotBlank(nodeName, "xml节点名称不能为空");

        Element node = element.addElement(nodeName);
        node.setText(MoreObjects.firstNonNull(value, ""));
        return node;
    }

    /**
     * 删除一个节点
     *
     * @param element
     * @param nodeName
     */
    public static void removeElement(Element element, String nodeName) {
        Element el = element.element(nodeName);
        if (el != null) {
            element.remove(el);
        }
    }

    /**
     * 删除节点列表
     *
     * @param element
     * @param nodeName
     */
    public static void removeElements(Element element, String nodeName) {
        List<Element> els = element.elements(nodeName);
        if (els != null && !els.isEmpty()) {
            els.forEach(item -> element.remove(item));
        }
    }

    /**
     * 获取节点值
     *
     * @param element 节点
     * @return
     */
    public static String getElementValue(Element element) {
        if (element == null) {
            return "";
        }
        return element.getText();
    }

    /**
     * 获取节点值
     *
     * @param element  父节点
     * @param nodeName 节点名称
     * @return
     */
    public static String getElementValue(Element element, String nodeName) {
        Element node = element.element(nodeName);
        if (node == null) {
            return "";
        }
        return node.getText();
    }

    /**
     * 添加属性
     *
     * @param element 节点
     * @param key     属性key
     * @param value   属性值
     */
    public static void addAttr(Element element, String key, String value) {
        Precondition.checkNotBlank(key, "xml属性key不能为空");

        element.addAttribute(key, MoreObjects.firstNonNull(value, ""));
    }

    /**
     * 移除属性
     *
     * @param element 节点
     * @param key     属性名称
     */
    public static void removeAttr(Element element, String key) {
        Precondition.checkNotBlank(key, "xml属性key不能为空");

        Attribute attr = element.attribute(key);
        if (attr != null) {
            element.remove(attr);
        }
    }

    /**
     * 获取属性值
     *
     * @param element
     * @param key
     * @return
     */
    public static String getAttrValue(Element element, String key) {
        Precondition.checkNotBlank(key, "xml属性key不能为空");

        return element.attributeValue(key);
    }

    /**
     * 增加注释
     *
     * @param doc
     * @param comment
     */
    public static void addComment(Document doc, String comment) {
        doc.addComment(MoreObjects.firstNonNull(comment, ""));
    }

    /**
     * 添加注释
     *
     * @param node
     * @param comment
     */
    public static void addComment(Element node, String comment) {
        node.addComment(MoreObjects.firstNonNull(comment, ""));
    }

    /**
     * 格式化输出
     *
     * @param doc document
     * @return string
     */
    public static String prettyPrint(Document doc) {
        // 这里设置缩进， 新行等属性
        OutputFormat format = OutputFormat.createPrettyPrint();
        //format.setEncoding(Charsets.UTF_8.toString());

        XMLWriter writer = null;
        StringWriter stringWriter = new StringWriter();
        try {
            writer = new XMLWriter(stringWriter, format);
            writer.write(doc);
        } catch (Exception e) {
            log.error("fail to write xml to string", e);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (Exception e) {
                    log.error("fail to close writer", e);
                }
            }
        }
        return stringWriter.toString();
    }


    /**
     * 是否是合法的xml 字符串
     *
     * @param xmlStr
     * @return
     */
    public static boolean isValid(String xmlStr) {
        boolean flag;
        try {
            SAXParserFactory.newInstance().newSAXParser().getXMLReader().parse(new InputSource(new StringReader(xmlStr)));
            flag = true;
        } catch (IOException | ParserConfigurationException | SAXException e) {
            flag = false;
            log.error("is not valid xml string", e);
        }
        return flag;
    }

    /**
     * 读取xml文件
     *
     * @param filePath xml 文件路径
     * @return document
     */
    public static Document read(String filePath) {
        SAXReader reader = new SAXReader();
        Document document = null;
        try {
            document = reader.read(new File(filePath));
        } catch (Exception e) {
            log.error("fail to read xml file, ex={}, file path={}", e, filePath);
        }

        return document;
    }

    /**
     * 读取xml字符串到Document
     *
     * @param xmlStr xml字符串
     * @return document
     */
    public static Document readStr(String xmlStr) {
        SAXReader reader = new SAXReader();
        Document document = null;
        try {
            document = reader.read(new StringReader(xmlStr));
        } catch (Exception e) {
            log.error("fail to read xml string", e);
        }

        return document;
    }


    /**
     * 将xml document 文件写入文件
     *
     * @param doc      xml document
     * @param filePath file path
     * @return boolean
     */
    public static boolean write(Document doc, String filePath) {
        boolean flag = false;
        try {
            // 打印配置
            OutputFormat format = OutputFormat.createPrettyPrint();
            //format.setEncoding(Charsets.UTF_8.toString());

            XMLWriter writer = new XMLWriter(new FileWriter(filePath), format);
            writer.write(doc);
            writer.close();
            flag = true;
        } catch (Exception e) {
            log.error("fail to write xml to file, ex={}, file path={}", e, filePath);
        }

        return flag;
    }

    //----- old

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
        if (root == null) {
            return null;
        }
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
        if (root == null) {
            return null;
        }
        try {
            T obj = null;

            // check basic
            String text = root.getText();
            if (EqualUtil.isEq(clazz.getName(), String.class.getName()) || EqualUtil.isEq(clazz.getName(), Integer.class.getName()) || EqualUtil.isEq(clazz.getName(), Long.class.getName()) || EqualUtil.isEq(clazz.getName(), Double.class.getName()) || EqualUtil.isEq(clazz.getName(), Float.class.getName()) || EqualUtil.isEq(clazz.getName(), Byte.class.getName()) || EqualUtil.isEq(clazz.getName(), Boolean.class.getName())) {
                // ignore node key.
                if (text != null) {
                    //TODO 基础类型通过构造函数直接生成
                    obj = clazz.getConstructor(String.class).newInstance(text.trim());
                }
                return obj;
            }

            obj = clazz.newInstance();

            Field[] fields = clazz.getDeclaredFields();
            Method setMethod;
            String fieldType;
            String fieldGenericType;
            String className;
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                fieldType = (field.getType() + "");
                setMethod = obj.getClass().getMethod("set" + fields[i].getName().substring(0, 1).toUpperCase() + fields[i].getName().substring(1), fields[i].getType());
                if ("interface java.util.List".equals(fieldType)) {
                    fieldGenericType = fields[i].getGenericType() + "";
                    String[] sp1 = fieldGenericType.split("<");
                    String[] sp2 = sp1[1].split(">");
                    className = sp2[0];
                    Element el = root.element(fields[i].getName());
                    if (el != null) {
                        Object listNode = getList(el, Class.forName(className));
                        setMethod.invoke(obj, listNode);
                    }
                    continue;
                }

                // check basic
                text = root.elementText(field.getName());
                if (field.getType().equals(String.class)) {
                    setMethod.invoke(obj, text);
                } else if (field.getType().equals(Integer.class) || field.getType().equals(int.class)) {
                    if (text != null) {
                        setMethod.invoke(obj, Integer.parseInt(text.trim()));
                    }
                } else if (field.getType().equals(Long.class) || field.getType().equals(long.class)) {
                    if (text != null) {
                        setMethod.invoke(obj, Long.parseLong(text.trim()));
                    }
                } else if (field.getType().equals(Double.class) || field.getType().equals(double.class)) {
                    if (text != null) {
                        setMethod.invoke(obj, Double.parseDouble(text.trim()));
                    }
                } else if (field.getType().equals(Float.class) || field.getType().equals(float.class)) {
                    if (text != null) {
                        setMethod.invoke(obj, Float.parseFloat(text.trim()));
                    }
                } else if (field.getType().equals(Byte.class) || field.getType().equals(Byte.class)) {
                    if (text != null) {
                        setMethod.invoke(obj, Byte.parseByte(text.trim()));
                    }
                } else if (field.getType().equals(Boolean.class) || field.getType().equals(boolean.class)) {
                    if (text != null) {
                        setMethod.invoke(obj, Boolean.valueOf(text.trim()));
                    }
                } else if (field.getType().equals(Date.class)) {
                    //TODO
                    if (text != null) {
                        setMethod.invoke(obj, Date.parse(text.trim()));
                    }
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
