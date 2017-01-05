package com.weibo.sysdata.dsalg.impl;

import com.weibo.sysdata.dsalg.DigitalSignatureAlgorithm;
import com.weibo.sysdata.dsalg.utils.DSAUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhengsheng on 2017/1/4.
 */
public class MD5 extends DigitalSignatureAlgorithm {

    /**
     * 验证签名
     *
     * @param h         数字签名
     * @param messages  源消息
     * @param publicKey 公钥
     * @param isSort    是否排序
     * @return
     */
    public boolean verify(String h, List<String> messages, String publicKey, boolean isSort) throws NoSuchAlgorithmException {
        boolean valid = false;
        final String request = DSAUtils.concatenate(messages, isSort);
        String md5 = generateSignature(new ArrayList<String>() {
            {
                add(request);
            }
        }, publicKey, true);
        if (h.equals(md5)) {
            valid = true;
        }
        return valid;
    }


    /**
     * 生成数字签名
     *
     * @param messages 源消息
     * @param key      密钥
     * @param isSort   是否排序
     * @return 签名
     */
    public String generateSignature(List<String> messages, String key, boolean isSort) throws NoSuchAlgorithmException {
        String request = DSAUtils.concatenate(messages, isSort);
        String signature = md5(key + request);
        return signature;
    }


    public static String md5(String src) throws NoSuchAlgorithmException {
        return md5(src.getBytes());
    }

    public static String md5(long[] array) throws NoSuchAlgorithmException {
        return md5(long2Byte(array));
    }

    public static String md5(byte[] bytes) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.reset();
        md5.update(bytes);
        byte[] digest = md5.digest();
        return encodeHex(digest);
    }

    /**
     * 转换字节至十六进制
     * @param bytes
     * @return
     */
    public static String encodeHex(byte[] bytes) {
        StringBuilder buf = new StringBuilder(bytes.length + bytes.length);
        for (int i = 0; i < bytes.length; i++) {
            if (((int) bytes[i] & 0xff) < 0x10) {
                buf.append("0");
            }
            buf.append(Long.toString((int) bytes[i] & 0xff, 16));
        }
        return buf.toString();
    }

    /**
     * 转换长整形至字节
     * @param longArray
     * @return
     */
    public static byte[] long2Byte(long[] longArray) {
        byte[] byteArray = new byte[longArray.length * 8];
        for (int i = 0; i < longArray.length; i++) {
            byteArray[0 + 8 * i] = (byte) (longArray[i] >> 56);
            byteArray[1 + 8 * i] = (byte) (longArray[i] >> 48);
            byteArray[2 + 8 * i] = (byte) (longArray[i] >> 40);
            byteArray[3 + 8 * i] = (byte) (longArray[i] >> 32);
            byteArray[4 + 8 * i] = (byte) (longArray[i] >> 24);
            byteArray[5 + 8 * i] = (byte) (longArray[i] >> 16);
            byteArray[6 + 8 * i] = (byte) (longArray[i] >> 8);
            byteArray[7 + 8 * i] = (byte) (longArray[i] >> 0);
        }
        return byteArray;
    }

    public static void main(String[] args) throws Exception {
        MD5 md5 = new MD5();
        final String message = "hello";
        final String TEST_KEY = "Wx2qiu1T$";

        String h = md5.generateSignature(new ArrayList<String>() {
            {
                add(message);
            }
        }, TEST_KEY);
        System.out.println(h);
        boolean valid = md5.verify(h, new ArrayList<String>() {
            {
                add(message);
            }
        }, TEST_KEY);
        System.out.println(valid);
    }
}
