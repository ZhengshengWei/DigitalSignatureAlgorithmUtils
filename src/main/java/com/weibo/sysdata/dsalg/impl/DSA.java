package com.weibo.sysdata.dsalg.impl;

import sun.misc.BASE64Encoder;

import java.math.BigInteger;
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

    public boolean verify(String h, List<String> messages, String publicKey, boolean isSort){
        return verify(h, messages, publicKey, isSort, DEFAULT_ALGORITHM , DEFAULT_SUB_ALGORITHM);
    }

    /**
     *
     * @param h
     * @param messages
     * @param publicKey
     * @param isSort
     * @param subAlgorithm  两种算法可供选择 SHA1withDSA/RawDSA
     * @return
     */
    public boolean verify(String h, List<String> messages, String publicKey, boolean isSort, String subAlgorithm){
        return verify(h, messages, publicKey, isSort, DEFAULT_ALGORITHM , subAlgorithm);
    }

    public String generateSignature(String message, String key) {
        return generateSignature(message, key, DEFAULT_ALGORITHM, DEFAULT_SUB_ALGORITHM);
    }

    /**
     *
     * @param message
     * @param key
     * @param subAlgorithm  两种算法可供选择 SHA1withDSA/RawDSA
     * @return
     */
    public  String generateSignature(String message, String key, String subAlgorithm){
        return generateSignature(message, key, DEFAULT_ALGORITHM, subAlgorithm);
    }

    public static List<String> generatePrivateAndPublicKey(){
        return generatePrivateAndPublicKey(DEFAULT_ALGORITHM);
    }

    /**
     * 生成公钥、私钥对
     * @return 数组中第一位为私钥
     */
    private static List<String> generatePrivateAndPublicKey(String algorithm){
        ArrayList<String> keyPairList = new ArrayList<String>();
        // 1.初始化密钥
        KeyPairGenerator keyPairGenerator = null;
        try {
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
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return keyPairList;
    }


    public static void main(String[] args){
        DSA dsa = new DSA();
        final String message = "hello";
        List<String> pairList = dsa.generatePrivateAndPublicKey();
        String publicKey = pairList.get(1);
        String privateKey = pairList.get(0);
        String sign = dsa.generateSignature(message, privateKey);
        System.out.println("DSA signature:" + sign);

        //verify
        boolean valid = dsa.verify(sign, new ArrayList<String>(){
            {
                add(message);
            }
        }, publicKey, true);

        System.out.println("result of valid:" + valid);
    }
}
