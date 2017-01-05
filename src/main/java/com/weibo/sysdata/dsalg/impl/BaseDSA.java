package com.weibo.sysdata.dsalg.impl;

import com.weibo.sysdata.dsalg.DigitalSignatureAlgorithm;
import com.weibo.sysdata.dsalg.utils.DSAUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.List;

/**
 * Created by zhengsheng on 2017/1/4.
 */
public abstract class BaseDSA extends DigitalSignatureAlgorithm {

    public static boolean verify(String h, List<String> messages, String publicKey, boolean isSort, String algorithm, String subAlgorithm) throws Exception {
        boolean valid = false;
        //用公钥进行验证签名
        PublicKey pKey = null;
        String request = DSAUtils.concatenate(messages, isSort);

        pKey = getPublicKey(publicKey, algorithm);
        byte[] hbytes = (new BASE64Decoder()).decodeBuffer(h);
        //声明签名对象
        Signature signature = Signature.getInstance(subAlgorithm);
        signature.initVerify(pKey);
        signature.update(request.getBytes());

        //验证签名
        valid = signature.verify(hbytes);
        return valid;
    }

    public static String generateSignature(List<String> messages, String key, String algorithm, String subAlgorithm, boolean isSort) throws Exception {
        byte[] result = new byte[0];
        String request = DSAUtils.concatenate(messages, isSort);
        //构造一个privateKey
        PrivateKey privateKey = getPrivateKey(key, algorithm);
        //声明签名的对象
        Signature signature = Signature.getInstance(subAlgorithm);
        signature.initSign(privateKey);
        signature.update(request.getBytes());
        //进行签名
        result = signature.sign();
        return (new BASE64Encoder()).encode(result);
    }

    private static PublicKey getPublicKey(String key, String algorithm) throws Exception {
        byte[] keyBytes;
        keyBytes = (new BASE64Decoder()).decodeBuffer(key);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }

    private static PrivateKey getPrivateKey(String key, String algorithm) throws Exception {
        byte[] keyBytes;
        keyBytes = (new BASE64Decoder()).decodeBuffer(key);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }

}
