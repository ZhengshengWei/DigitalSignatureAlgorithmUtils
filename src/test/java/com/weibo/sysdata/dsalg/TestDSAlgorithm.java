package com.weibo.sysdata.dsalg;



import com.weibo.sysdata.dsalg.impl.ECDSA;
import com.weibo.sysdata.dsalg.impl.RSA;
import com.weibo.sysdata.dsalgfactory.DSAFactory;
import com.weibo.sysdata.dsalgfactory.DSASet;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhengsheng on 2017/1/5.
 */
public class TestDSAlgorithm extends TestCase {

    @Before
    public void setup(){

    }
    
    @Test
    public void testMD5(){

    }


    @Test
    public void testRSA(){
        RSA rsa = new RSA();
        final String message = "hello";
        List<String> pairList = rsa.generatePrivateAndPublicKey();
        String publicKey = pairList.get(1);
        String privateKey = pairList.get(0);
        String sign = rsa.generateSignature(message, privateKey);
        System.out.println("RSA signature:" + sign);

        //verify
        boolean valid = rsa.verify(sign, new ArrayList<String>(){
            {
                add(message);
            }
        }, publicKey, true);

        System.out.println("result of valid:" + valid);
    }

    @Test
    public void testECDSA(){
        ECDSA ecds = new ECDSA();
        final String message = "hello";
        List<String> pairList = ecds.generatePrivateAndPublicKey();
        String publicKey = pairList.get(1);
        String privateKey = pairList.get(0);
        String sign = ecds.generateSignature(message, privateKey);
        System.out.println("ECDSA signature:" + sign);

        //verify
        boolean valid = DSAFactory.getInstance(DSASet.ECDSA).verify(sign, new ArrayList<String>(){
            {
                add(message);
            }
        }, publicKey, true);

        System.out.println("result of valid:" + valid);
    }
}
