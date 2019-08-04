package com.github.spy.sea.core.util;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
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
 * 二维码生成工具
 *
 * @author spy
 * @version 1.0
 * @since 1.0
 */
@Slf4j
public final class QRCodeUtil {

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

        Map<EncodeHintType, Object> hints = getEndcodeHints();

        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);// 生成矩阵
        Path path = FileSystems.getDefault().getPath(filePath);
        MatrixToImageWriter.writeToPath(bitMatrix, DEFAULT_FORMAT, path);// 输出图像
    }


    /**
     * 返回一个 BufferedImage 对象
     *
     * @param content 二维码内容
     * @param width   宽
     * @param height  高
     */
    public static BufferedImage toBufferedImage(String content, int width, int height) throws WriterException, IOException {
        Map<EncodeHintType, Object> hints = getEndcodeHints();

        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
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
        Map<EncodeHintType, Object> hints = getEndcodeHints();

        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
        MatrixToImageWriter.writeToStream(bitMatrix, DEFAULT_FORMAT, stream);
    }


    private static Map<EncodeHintType, Object> getEndcodeHints() {
        Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);// 容错等级 L、M、Q、H 其中 L 为最低, H 为最高
        hints.put(EncodeHintType.MARGIN, 1);// 二维码与图片边距

        return hints;
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
        BufferedImage image;
        image = ImageIO.read(new File(filePath));
        LuminanceSource source = new BufferedImageLuminanceSource(image);
        Binarizer binarizer = new HybridBinarizer(source);
        BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);

        Map<DecodeHintType, Object> hints = getDecodeHint();

        return new MultiFormatReader().decode(binaryBitmap, hints);// 对图像进行解码
    }

    private static Map<DecodeHintType, Object> getDecodeHint() {
        Map<DecodeHintType, Object> hints = new HashMap<DecodeHintType, Object>();
        hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");

        return hints;
    }


}
