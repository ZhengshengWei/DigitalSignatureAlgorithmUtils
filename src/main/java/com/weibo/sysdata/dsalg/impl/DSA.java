package com.weibo.sysdata.dsalg.impl;

import sun.misc.BASE64Encoder;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhengsheng on 2017/1/5.
 */
public class DSA extends BaseDSA {

    private static final String DEFAULT_SUB_ALGORITHM = "SHA1withDSA";
    private static final String DEFAULT_ALGORITHM = "DSA";

    public static boolean verify(String h, List<String> messages, String publicKey, boolean isSort) throws Exception {
        return verify(h, messages, publicKey, isSort, DEFAULT_ALGORITHM, DEFAULT_SUB_ALGORITHM);
    }

    /**
     * @param h            数字签名
     * @param messages
     * @param publicKey
     * @param isSort
     * @param subAlgorithm 两种算法可供选择 SHA1withDSA/RawDSA
     * @return
     */
    public static boolean verify(String h, List<String> messages, String publicKey, boolean isSort, String subAlgorithm) throws Exception {
        return verify(h, messages, publicKey, isSort, DEFAULT_ALGORITHM, subAlgorithm);
    }

    public static boolean verify(String h, List<String> messages, String publicKey) throws Exception {
        return verify(h, messages, publicKey, true, DEFAULT_ALGORITHM, DEFAULT_SUB_ALGORITHM);
    }

    /**
     * @param messages
     * @param key
     * @param subAlgorithm 两种算法可供选择 SHA1withDSA/RawDSA
     * @return
     */
    public static String generateSignature(List<String> messages, String key, String subAlgorithm, boolean isSort) throws Exception {
        return generateSignature(messages, key, DEFAULT_ALGORITHM, subAlgorithm, isSort);
    }

    public static String generateSignature(List<String> messages, String key, boolean isSort) throws Exception {
        return generateSignature(messages, key, DEFAULT_ALGORITHM, DEFAULT_SUB_ALGORITHM, isSort);
    }

    public static String generateSignature(List<String> messages, String key) throws Exception {
        return generateSignature(messages, key, true);
    }

    public static List<String> generatePrivateAndPublicKey() throws NoSuchAlgorithmException {
        return generatePrivateAndPublicKey(DEFAULT_ALGORITHM);
    }

    /**
     * 生成公钥、私钥对
     *
     * @return 数组中第一位为私钥
     */
    private static List<String> generatePrivateAndPublicKey(String algorithm) throws NoSuchAlgorithmException {
        ArrayList<String> keyPairList = new ArrayList<String>();
        // 1.初始化密钥
        KeyPairGenerator keyPairGenerator = null;
        keyPairGenerator = KeyPairGenerator
                .getInstance(algorithm);
        //设置KEY的长度
        keyPairGenerator.initialize(512);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        //得到公钥
        DSAPublicKey dsaPublicKey = (DSAPublicKey) keyPair.getPublic();
        //得到私钥
        DSAPrivateKey dsaPrivateKey = (DSAPrivateKey) keyPair.getPrivate();
        String publicKey = (new BASE64Encoder()).encode((dsaPublicKey.getEncoded()));
        String privateKey = (new BASE64Encoder()).encode((dsaPrivateKey.getEncoded()));
        keyPairList.add(privateKey);
        keyPairList.add(publicKey);
        return keyPairList;
    }


    public static void main(String[] args) throws Exception {
        final String message = "hello";
        List<String> pairList = DSA.generatePrivateAndPublicKey();
        String publicKey = pairList.get(1);
        String privateKey = pairList.get(0);
        String sign = DSA.generateSignature(new ArrayList<String>() {
            {
                add(message);
            }
        }, privateKey);
        System.out.println("DSA signature:" + sign);

        //verify
        boolean valid = DSA.verify(sign, new ArrayList<String>() {
            {
                add(message);
            }
        }, publicKey);

        System.out.println("result of valid:" + valid);
    }
}
