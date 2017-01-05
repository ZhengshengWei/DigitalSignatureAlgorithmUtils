package com.weibo.sysdata.dsalg.impl;

import cn.sina.api.commons.util.MD5Utils;
import com.weibo.sysdata.dsalg.DigitalSignatureAlgorithm;
import com.weibo.sysdata.dsalg.utils.DSAUtils;

import java.util.List;

/**
 * Created by zhengsheng on 2017/1/4.
 */
public class MD5 implements DigitalSignatureAlgorithm {

    public boolean verify(String h, List<String> messages, String publicKey, boolean isSort) {
        boolean valid = false;
        String request = "";
        request = DSAUtils.concatenate(messages, isSort);
        String md5 = generateSignature(request, publicKey);
        if (h.equals(md5)) {
            valid = true;
        }
        return valid;
    }

    public String generateSignature(String message, String key) {
        String signature = "";
        signature = MD5Utils.md5(key + message);
        return signature;
    }
}
