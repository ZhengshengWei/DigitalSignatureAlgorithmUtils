package com.weibo.sysdata.dsalg;

import java.util.List;

/**
 * Created by zhengsheng on 2017/1/4.
 */
public interface DigitalSignatureAlgorithm {

    /**
     * 验证签名
     * @param h 数字签名
     * @param messages 源消息
     * @param publicKey 公钥
     * @param isSort 是否排序
     * @return
     */
    public boolean verify(String h, List<String> messages, String publicKey ,boolean isSort);


    /**
     * 生成数字签名
     * @param message 源消息
     * @param  key 密钥
     * @return 签名
     */
    public String generateSignature(String message, String key);

}
