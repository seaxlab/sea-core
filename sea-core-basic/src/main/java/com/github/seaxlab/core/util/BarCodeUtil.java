package com.github.seaxlab.core.util;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.oned.EAN13Writer;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/2/26
 * @since 1.0
 */
@Slf4j
public final class BarCodeUtil {


    /**
     * 默认图像类型
     */
    private static final String DEFAULT_FORMAT = "png";

    /**
     * 生成二维码图片
     *
     * @param filePath 文件路径
     * @param content  二维码中内容
     */
    public static void encode(String filePath, String content) throws IOException, WriterException {
        encode(filePath, content, 200, 200);
    }

    /**
     * 生成二维码图片
     *
     * @param filePath
     * @param content
     * @param height
     * @param width
     * @throws IOException
     * @throws WriterException
     */
    public static void encode(String filePath, String content, int height, int width) throws IOException, WriterException {
        height = height < 0 ? 300 : height;
        width = width < 0 ? 150 : width;

        EAN13Writer barcodeWriter = new EAN13Writer();
        BitMatrix bitMatrix = barcodeWriter.encode(content, BarcodeFormat.EAN_13, height, width, getEncodeHints());

        Path path = FileSystems.getDefault().getPath(filePath);
        // 输出图像
        MatrixToImageWriter.writeToPath(bitMatrix, DEFAULT_FORMAT, path);
    }


    /**
     * 返回一个 BufferedImage 对象
     *
     * @param content 二维码内容
     * @param width   宽
     * @param height  高
     */
    public static BufferedImage toBufferedImage(String content, int width, int height) throws WriterException {
        Map<EncodeHintType, Object> hints = getEncodeHints();

        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.EAN_13, width, height, hints);
        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }

    /**
     * 将二维码图片输出到一个流中
     *
     * @param content 二维码内容
     * @param stream  输出流
     * @param width   宽
     * @param height  高
     */
    public static void writeToStream(String content, OutputStream stream, int width, int height) throws WriterException, IOException {
        Map<EncodeHintType, Object> hints = getEncodeHints();

        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.EAN_13, width, height, hints);
        MatrixToImageWriter.writeToStream(bitMatrix, DEFAULT_FORMAT, stream);
    }


    /**
     * 对二维码进行解码，获取内容
     *
     * @param filePath
     * @return
     * @throws IOException
     * @throws NotFoundException
     */
    public static String decode(String filePath) throws IOException, NotFoundException {
        Result result = decodeResult(filePath);

        return result.getText();
    }

    public static Result decodeResult(String filePath) throws IOException, NotFoundException {
        //TODO confirm? not working.
        BufferedImage image = ImageIO.read(new File(filePath));
        LuminanceSource source = new BufferedImageLuminanceSource(image);
        Binarizer binarizer = new HybridBinarizer(source);
        BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);

//        Map<DecodeHintType, Object> hints = getDecodeHint();

        // 对图像进行解码
        return new MultiFormatReader().decode(binaryBitmap);
    }


    private static Map<EncodeHintType, Object> getEncodeHints() {
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        // 容错等级 L、M、Q、H 其中 L 为最低, H 为最高
//        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);

        // 二维码与图片边距
//        hints.put(EncodeHintType.MARGIN, 1);

        return hints;
    }

    private static Map<DecodeHintType, Object> getDecodeHint() {
        Map<DecodeHintType, Object> hints = new HashMap<>();
        hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");

        return hints;
    }

}
