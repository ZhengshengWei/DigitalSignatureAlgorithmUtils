package com.weibo.sysdata.dsalg.impl;

import com.weibo.sysdata.dsalg.DigitalSignatureAlgorithm;
import com.weibo.sysdata.dsalg.utils.DSAUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.List;

/**
 * Created by zhengsheng on 2017/1/4.
 */
public abstract class BaseDSA implements DigitalSignatureAlgorithm {

    public boolean verify(String h, List<String> messages, String publicKey, boolean isSort, String algorithm, String subAlgorithm) {
        boolean valid = false;
        //用公钥进行验证签名
        PublicKey pKey = null;
        String request = DSAUtils.concatenate(messages, isSort);
        try {
            pKey = getPublicKey(publicKey, algorithm);
            byte[] hbytes =  (new BASE64Decoder()).decodeBuffer(h);
            //声明签名对象
            Signature signature = Signature.getInstance(subAlgorithm);
            signature.initVerify(pKey);
            signature.update(request.getBytes());

            //验证签名
            valid = signature.verify(hbytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return valid;
    }

    public String generateSignature(String message, String key, String algorithm, String subAlgorithm) {
        byte[] result = new byte[0];
        try {
            //构造一个privateKey
            PrivateKey privateKey = getPrivateKey(key, algorithm);
            //声明签名的对象
            Signature signature = Signature.getInstance(subAlgorithm);
            signature.initSign(privateKey);
            signature.update(message.getBytes());
            //进行签名
            result = signature.sign();
        } catch (SignatureException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (new BASE64Encoder()).encode(result);
    }

    private  PublicKey getPublicKey(String key, String algorithm) throws Exception {
        byte[] keyBytes;
        keyBytes = (new BASE64Decoder()).decodeBuffer(key);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }

    private PrivateKey getPrivateKey(String key, String algorithm) throws Exception {
        byte[] keyBytes;
        keyBytes = (new BASE64Decoder()).decodeBuffer(key);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }

}
