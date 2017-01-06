package com.weibo.sysdata.dsalg;


import com.weibo.sysdata.dsalg.impl.DSA;
import com.weibo.sysdata.dsalg.impl.ECDSA;
import com.weibo.sysdata.dsalg.impl.MD5;
import com.weibo.sysdata.dsalg.impl.RSA;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.security.NoSuchAlgorithmException;
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
    public void testMD5() throws NoSuchAlgorithmException {
        final String message = "hello";
        final String TEST_KEY = "Wx2qiu1T$";

        String h = MD5.generateSignature(new ArrayList<String>() {
            {
                add(message);
            }
        }, TEST_KEY);
        System.out.println(h);
        boolean valid = MD5.verify(h, new ArrayList<String>() {
            {
                add(message);
            }
        }, TEST_KEY);
        System.out.println(valid);
    }


    @Test
    public void testRSA() throws Exception {
        final String message = "hello";
        List<String> pairList = RSA.generatePrivateAndPublicKey();
        String publicKey = pairList.get(1);
        String privateKey = pairList.get(0);

        String sign = RSA.generateSignature(new ArrayList<String>() {
            {
                add(message);
            }
        }, privateKey);
        System.out.println("RSA signature:" + sign);

        //verify
        boolean valid = RSA.verify(sign, new ArrayList<String>() {
            {
                add(message);
            }
        }, publicKey);

        System.out.println("result of valid:" + valid);
    }

    @Test
    public void testDSA() throws Exception {
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

    @Test
    public void testECDSA() throws Exception {
        final String message = "hello";
        List<String> pairList = ECDSA.generatePrivateAndPublicKey();
        String publicKey = pairList.get(1);
        String privateKey = pairList.get(0);
        String sign = ECDSA.generateSignature(new ArrayList<String>(){
            {
                add(message);
            }
        }, privateKey);
        System.out.println("ECDSA signature:" + sign);


        //verify
        boolean valid = ECDSA.verify(sign, new ArrayList<String>(){
            {
                add(message);
            }
        }, publicKey, true);

        System.out.println("result of valid:" + valid);
    }

    public void testOP(){

    }
}
