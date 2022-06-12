package com.github.seaxlab.core.component.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * 模块名
 *
 * @author shi.pengyan
 * @version 1.0 2017-08-09 7:47
 * @since 1.0
 */
@Slf4j
public class SimplePDFTest {

    private Document document;
    private PdfWriter writer;

    @Before
    public void setUp() throws FileNotFoundException, DocumentException {
        //设置纸张
        Rectangle rect = new Rectangle(PageSize.A4);
        //创建文件
        document = new Document(rect);
        //建立一个书写器
        writer = PdfWriter.getInstance(document, new FileOutputStream("C:/test.pdf"));
    }

    @After
    public void destroy() {
        //关闭文档
        document.close();
        writer.close();

        log.debug("create successfully");
    }


    @Test
    public void simpleTest1() throws Exception {
        //打开文件
        document.open();
        //添加内容
        document.add(new Paragraph("This is spy-framework test"));

        //设置属性
        //标题
        document.addTitle("this is a title");
        //作者
        document.addAuthor("shi.pengyan");
        //主题
        document.addSubject("spy-framework");
        //关键字
        document.addKeywords("spy,framework");
        //创建时间
        document.addCreationDate();
        //应用程序
        document.addCreator("spy-framework auto generator");
    }


    @Test
    public void simpleTest2() throws Exception {
        //打开文件
        document.open();

        //中文字体,解决中文不能显示问题
        BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);

        //蓝色字体
        Font blueFont = new Font(bfChinese);
        blueFont.setColor(BaseColor.BLUE);
        //段落文本
        Paragraph paragraphBlue = new Paragraph("中文段落", blueFont);
        document.add(paragraphBlue);

        //绿色字体
        Font greenFont = new Font(bfChinese);
        greenFont.setColor(BaseColor.GREEN);
        //创建章节
        Paragraph chapterTitle = new Paragraph("段落标题xxxx", greenFont);
        Chapter chapter1 = new Chapter(chapterTitle, 1);
        chapter1.setNumberDepth(0);

        Paragraph sectionTitle = new Paragraph("部分标题", greenFont);
        Section section1 = chapter1.addSection(sectionTitle);

        Paragraph sectionContent = new Paragraph("部分内容", blueFont);
        section1.add(sectionContent);

        chapter1.addSection(sectionContent);

        //将章节添加到文章中
        document.add(chapter1);

    }


    @Test
    public void encryptTest() throws Exception {

        //用户密码
        String userPassword = "123456";
        //拥有者密码
        String ownerPassword = "1122";

        writer.setEncryption(userPassword.getBytes(), ownerPassword.getBytes(), PdfWriter.ALLOW_PRINTING,
                PdfWriter.ENCRYPTION_AES_128);

        // 打开文件
        document.open();

        //添加内容
        document.add(new Paragraph("password !!!!"));
    }


}
