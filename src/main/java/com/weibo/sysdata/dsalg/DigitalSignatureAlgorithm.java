package com.weibo.sysdata.dsalg;

import java.util.List;

/**
 * Created by zhengsheng on 2017/1/4.
 */
public abstract class DigitalSignatureAlgorithm {

    /**
     * 验证签名
     * @param h 数字签名
     * @param messages 源消息
     * @param publicKey 公钥
     * @param isSort 是否排序
     * @return
     */
    public abstract boolean verify(String h, List<String> messages, String publicKey ,boolean isSort) throws Exception;


    /**
     * 验证签名 默认将message信息按升序排列
     * @param h 数字签名
     * @param messages 源消息
     * @param publicKey 公钥
     * @return
     */
    public  boolean verify(String h, List<String> messages, String publicKey) throws Exception {
        return verify(h, messages, publicKey, true);
    }


    /**
     * 生成数字签名
     * @param messages 源消息
     * @param  key 密钥
     * @param  isSort 是否排序
     * @return 签名
     */
    public abstract String generateSignature(List<String> messages, String key, boolean isSort) throws Exception;


    /**
     * 生成数字签名 默认将message信息按升序排列
     * @param messages 源消息
     * @param  key 密钥
     * @return 签名
     */
    public String generateSignature(List<String> messages, String key) throws Exception {
        return generateSignature(messages, key, true);
    }

}
