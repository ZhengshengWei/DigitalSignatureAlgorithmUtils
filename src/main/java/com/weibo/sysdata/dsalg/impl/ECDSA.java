package com.weibo.sysdata.dsalg.impl;

import sun.misc.BASE64Encoder;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhengsheng on 2017/1/5.
 */
public class ECDSA extends BaseDSA {

    private static final String DEFAULT_SUB_ALGORITHM = "SHA1withECDSA";
    private static final String DEFAULT_ALGORITHM = "EC";

    public  boolean verify(String h, List<String> messages, String publicKey, boolean isSort) throws Exception {
        return verify(h, messages, publicKey, isSort, DEFAULT_ALGORITHM , DEFAULT_SUB_ALGORITHM);
    }

    /**
     *
     * @param h             数字签名
     * @param messages
     * @param publicKey
     * @param isSort
     * @param subAlgorithm  两种算法可供选择 SHA1withDSA/RawDSA
     * @return
     */
    public boolean verify(String h, List<String> messages, String publicKey, boolean isSort, String subAlgorithm) throws Exception {
        return verify(h, messages, publicKey, isSort, DEFAULT_ALGORITHM , subAlgorithm);
    }

    public  String generateSignature(List<String> messages, String key, boolean isSort) throws Exception {
        return generateSignature(messages, key, DEFAULT_ALGORITHM, DEFAULT_SUB_ALGORITHM, isSort);
    }

    /**
     *
     * @param messages
     * @param key
     * @param subAlgorithm  两种算法可供选择 SHA1withDSA/RawDSA
     * @return
     */
    public  String generateSignature(List<String> messages, String key, String subAlgorithm, boolean isSort) throws Exception {
        return generateSignature(messages, key, DEFAULT_ALGORITHM, subAlgorithm, isSort);
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
            keyPairGenerator.initialize(256);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            //得到公钥
            ECPublicKey ecPublicKey = (ECPublicKey) keyPair.getPublic();
            //得到私钥
            ECPrivateKey ecPrivateKey = (ECPrivateKey) keyPair.getPrivate();
            String publicKey = (new BASE64Encoder()).encode((ecPublicKey.getEncoded()));
            String privateKey = (new BASE64Encoder()).encode((ecPrivateKey.getEncoded()));
            keyPairList.add(privateKey);
            keyPairList.add(publicKey);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return keyPairList;
    }


    public static void main(String[] args) throws Exception {
        ECDSA ecds = new ECDSA();
        final String message = "hello";
        List<String> pairList = ecds.generatePrivateAndPublicKey();
        String publicKey = pairList.get(1);
        String privateKey = pairList.get(0);
        String sign = ecds.generateSignature(new ArrayList<String>(){
            {
                add(message);
            }
        }, privateKey);
        System.out.println("ECDSA signature:" + sign);

        //verify
        boolean valid = ecds.verify(sign, new ArrayList<String>(){
            {
                add(message);
            }
        }, publicKey, true);

        System.out.println("result of valid:" + valid);
    }
}
