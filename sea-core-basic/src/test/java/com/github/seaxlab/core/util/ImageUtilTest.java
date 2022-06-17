package com.github.seaxlab.core.util;

import com.github.seaxlab.core.BaseCoreTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.awt.*;

/**
 * 模块名
 *
 * @author shi.pengyan
 * @version 1.0 2017-08-17 8:47
 * @since 1.0
 */
@Slf4j
public class ImageUtilTest extends BaseCoreTest {

    private static final String IMG_SRC = "c:/1.png";

    @Test
    public void run16() throws Exception {
        // 1-缩放图像：
        // 方法一：按比例缩放
        ImageUtil.scale(IMG_SRC, "c:/test/abc_scale.jpg", 2, true);//测试OK
        // 方法二：按高度和宽度缩放
        ImageUtil.scale2(IMG_SRC, "c:/test/abc_scale2.jpg", 500, 300, true);//测试OK
        // 2-切割图像：
        // 方法一：按指定起点坐标和宽高切割
        ImageUtil.cut(IMG_SRC, "c:/test/abc_cut.jpg", 0, 0, 400, 400);//测试OK
        // 方法二：指定切片的行数和列数
        ImageUtil.cut2(IMG_SRC, "c:/test/", 2, 2);//测试OK
        // 方法三：指定切片的宽度和高度
        ImageUtil.cut3(IMG_SRC, "c:/test/", 300, 300);//测试OK
        // 3-图像类型转换：
        ImageUtil.convert(IMG_SRC, "GIF", "c:/test/abc_convert.gif");//测试OK
        // 4-彩色转黑白：
//        ImageUtil.gray(IMG_SRC, "c:/test/abc_gray.jpg");//测试OK
        // 5-给图片添加文字水印：
        // 方法一：
        ImageUtil.pressText("我是水印文字", IMG_SRC, "c:/test/abc_pressText.jpg", "宋体", Font.BOLD, Color.black, 80, 0, 0, 0.5f);//测试OK
        // 方法二：
        ImageUtil.pressText2("我也是水印文字", IMG_SRC, "c:/test/abc_pressText2.jpg", "黑体", 36, Color.black, 80, 0, 0, 0.5f);//测试OK

        // 6-给图片添加图片水印：
        ImageUtil.pressImage(IMG_SRC, IMG_SRC, "c:/test/abc_pressImage.jpg", 0, 0, 0.5f);//测试OK
    }
}
