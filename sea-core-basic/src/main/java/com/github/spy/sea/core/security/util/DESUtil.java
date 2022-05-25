package com.github.spy.sea.core.security.util;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * DES util
 *
 * @author spy
 * @version 1.0 2022/5/24
 * @since 1.0
 */
@Slf4j
public class DESUtil {

    private static final String DEFAULT_KEY = "65ac7z96";

    //DES_CBC_Encrypt

    /**
     * 使用默认key加密
     *
     * @param content
     * @return
     */
    public static String encrypt(String content) {
        return encrypt(content, DEFAULT_KEY);
    }

    /**
     * 使用默认key解密
     *
     * @param content
     * @return
     */
    public static String decrypt(String content) {
        return decrypt(content, DEFAULT_KEY);
    }


    /**
     * encrypt
     *
     * @param content   内容
     * @param secretKey 重点：必须是8位
     * @return
     */
    public static String encrypt(String content, String secretKey) {
        try {
            byte[] keyBytes = secretKey.getBytes();
            byte[] text = content.getBytes();
            DESKeySpec keySpec = new DESKeySpec(keyBytes);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey key = keyFactory.generateSecret(keySpec);

            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(keySpec.getKey()));
            byte[] result = cipher.doFinal(text);
            return byteToHexString(result);
        } catch (Exception e) {
            log.error("fail to encrypt by DES", e);
        }
        return null;
    }

    /**
     * decrypt
     *
     * @param content   待解密内容
     * @param secretKey 秘钥
     * @return
     */
    public static String decrypt(String content, String secretKey) {
        try {
            byte[] keyBytes = secretKey.getBytes();
            byte[] text = hexToByteArray(content);
            DESKeySpec keySpec = new DESKeySpec(keyBytes);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey key = keyFactory.generateSecret(keySpec);

            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(keyBytes));
            byte[] result = cipher.doFinal(text);
            return new String(result);
        } catch (Exception e) {
            log.error("fail to decrypt by DES", e);
        }
        return null;
    }

    private static String byteToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length);
        String sTemp;
        for (byte aByte : bytes) {
            sTemp = Integer.toHexString(0xFF & aByte);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }

    private static byte[] hexToByteArray(String inHex) {
        int hexLen = inHex.length();
        byte[] result;
        if (hexLen % 2 == 1) {
            hexLen++;
            result = new byte[(hexLen / 2)];
            inHex = "0" + inHex;
        } else {
            result = new byte[(hexLen / 2)];
        }
        int j = 0;
        for (int i = 0; i < hexLen; i += 2) {
            result[j] = (byte) Integer.parseInt(inHex.substring(i, i + 2), 16);
            j++;
        }
        return result;
    }

    //public static String encrypt(String text) {
    //    try {
    //        KeyGenerator keygenerator = KeyGenerator.getInstance("DES");
    //        SecretKey myDesKey = keygenerator.generateKey();
    //
    //        // Create the cipher
    //        Cipher desCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
    //
    //        // Initialize the cipher for encryption
    //        desCipher.init(Cipher.ENCRYPT_MODE, myDesKey);
    //
    //        //sensitive information
    //        return Base64.encodeBase64String(text.getBytes());
    //    } catch (Exception e) {
    //        log.warn("fail to encrypt by DES", e);
    //    }
    //    return "";
    //}
    //
    //
    //public static String decrypt(String text) {
    //
    //    try {
    //        byte[] bytes = Base64.decodeBase64(text.getBytes());
    //        KeyGenerator keygenerator = KeyGenerator.getInstance("DES");
    //        SecretKey myDesKey = keygenerator.generateKey();
    //
    //        Cipher desCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
    //
    //        // Initialize the same cipher for decryption
    //        desCipher.init(Cipher.DECRYPT_MODE, myDesKey);
    //
    //        // Decrypt the text
    //        byte[] textDecrypted = desCipher.doFinal(bytes);
    //
    //        return new String(textDecrypted);
    //    } catch (Exception e) {
    //        log.error("fail to decrypt by DES", e);
    //    }
    //    return "";
    //}

}
