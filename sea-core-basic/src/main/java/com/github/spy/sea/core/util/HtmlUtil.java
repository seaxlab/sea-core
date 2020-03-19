package com.github.spy.sea.core.util;

import lombok.extern.slf4j.Slf4j;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/3/19
 * @see cn.hutool.http.HtmlUtil
 * @since 1.0
 */
@Slf4j
public final class HtmlUtil {

    private HtmlUtil() {
    }


    /**
     * 转义文本中的HTML字符为安全的字符，以下字符被转义：
     * <ul>
     * <li>' 替换为 &amp;#039; (&amp;apos; doesn't work in HTML4)</li>
     * <li>" 替换为 &amp;quot;</li>
     * <li>&amp; 替换为 &amp;amp;</li>
     * <li>&lt; 替换为 &amp;lt;</li>
     * <li>&gt; 替换为 &amp;gt;</li>
     * </ul>
     *
     * @param text 被转义的文本
     * @return 转义后的文本
     */
    public static String escape(String text) {
        return cn.hutool.http.HtmlUtil.escape(text);
    }

    /**
     * 还原被转义的HTML特殊字符
     *
     * @param htmlStr 包含转义符的HTML内容
     * @return 转换后的字符串
     */
    public static String unescape(String htmlStr) {
        return cn.hutool.http.HtmlUtil.unescape(htmlStr);
    }

    // ---------------------------------------------------------------- encode text

    /**
     * 清除所有HTML标签，但是不删除标签内的内容
     *
     * @param content 文本
     * @return 清除标签后的文本
     */
    public static String cleanHtmlTag(String content) {
        return cn.hutool.http.HtmlUtil.cleanHtmlTag(content);
    }

    /**
     * 清除指定HTML标签和被标签包围的内容<br>
     * 不区分大小写
     *
     * @param content  文本
     * @param tagNames 要清除的标签
     * @return 去除标签后的文本
     */
    public static String removeHtmlTag(String content, String... tagNames) {
        return cn.hutool.http.HtmlUtil.removeHtmlTag(content, tagNames);
    }

    /**
     * 清除指定HTML标签，不包括内容<br>
     * 不区分大小写
     *
     * @param content  文本
     * @param tagNames 要清除的标签
     * @return 去除标签后的文本
     */
    public static String unwrapHtmlTag(String content, String... tagNames) {
        return cn.hutool.http.HtmlUtil.unwrapHtmlTag(content, tagNames);
    }

    /**
     * 清除指定HTML标签<br>
     * 不区分大小写
     *
     * @param content        文本
     * @param withTagContent 是否去掉被包含在标签中的内容
     * @param tagNames       要清除的标签
     * @return 去除标签后的文本
     */
    public static String removeHtmlTag(String content, boolean withTagContent, String... tagNames) {
        return cn.hutool.http.HtmlUtil.removeHtmlTag(content, withTagContent, tagNames);
    }

    /**
     * 去除HTML标签中的属性，如果多个标签有相同属性，都去除
     *
     * @param content 文本
     * @param attrs   属性名（不区分大小写）
     * @return 处理后的文本
     */
    public static String removeHtmlAttr(String content, String... attrs) {
        return cn.hutool.http.HtmlUtil.removeHtmlAttr(content, attrs);
    }

    /**
     * 去除指定标签的所有属性
     *
     * @param content  内容
     * @param tagNames 指定标签
     * @return 处理后的文本
     */
    public static String removeAllHtmlAttr(String content, String... tagNames) {
        return cn.hutool.http.HtmlUtil.removeAllHtmlAttr(content, tagNames);
    }

    /**
     * 过滤HTML文本，防止XSS攻击
     *
     * @param htmlContent HTML内容
     * @return 过滤后的内容
     */
    public static String filter(String htmlContent) {
        return cn.hutool.http.HtmlUtil.filter(htmlContent);
    }
}
