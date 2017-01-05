package com.weibo.sysdata.dsalgfactory;

import com.weibo.sysdata.dsalg.DigitalSignatureAlgorithm;
import com.weibo.sysdata.dsalg.impl.DSA;
import com.weibo.sysdata.dsalg.impl.ECDSA;
import com.weibo.sysdata.dsalg.impl.MD5;
import com.weibo.sysdata.dsalg.impl.RSA;

import java.util.ArrayList;

/**
 * Created by zhengsheng on 2017/1/4.
 */
public class DSAFactory {

    private static DigitalSignatureAlgorithm dsa;

    private static class MD5Holder{
        public static MD5 md5 = new MD5();
    }

    private  static  class RSAHolder{
        public static RSA rsa = new RSA();
    }

    private  static  class DSAHolder{
        public static DSA dsa = new DSA();
    }

    private  static  class ECDSAHolder{
        public static ECDSA ecdsa = new ECDSA();
    }

    public static DigitalSignatureAlgorithm getInstance(DSASet dsaSet){
        if(dsaSet == DSASet.MD5) {
            dsa = MD5Holder.md5;
        }else if(dsaSet == DSASet.RSA) {
            dsa = RSAHolder.rsa;
        }else if(dsaSet == DSASet.DSA) {
            dsa = DSAHolder.dsa;
        }else if(dsaSet == DSASet.ECDSA) {
            dsa = ECDSAHolder.ecdsa;
        }
        return dsa;
    }

    public static void main(String[] args){
        final String message = "hello";
        final String TEST_KEY = "Wx2qiu1T$";

        String h = DSAFactory.getInstance(DSASet.MD5).generateSignature(message, TEST_KEY);
        System.out.println(h);
        boolean valid = DSAFactory.getInstance(DSASet.MD5).verify(h, new ArrayList<String>(){
            {
                add(message);
            }
        }, TEST_KEY, true);
        System.out.println(valid);
    }
}
