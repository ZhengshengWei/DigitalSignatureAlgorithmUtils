package com.weibo.sysdata.dsalg;


import com.weibo.sysdata.dsalgfactory.DSAFactory;
import com.weibo.sysdata.dsalgfactory.DSASet;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by zhengsheng on 2017/1/5.
 */
public class TestDSAFactory extends TestCase {

    @Before
    public void setup(){

    }

    @Test
    public void testFactory() throws Exception {
        final String message = "hello";
        final String TEST_KEY = "Wx2qiu1T$";

        String h = DSAFactory.getInstance(DSASet.MD5).generateSignature(new ArrayList<String>(){
            {
                add(message);
            }
        }, TEST_KEY);
        System.out.println(h);
        boolean valid = DSAFactory.getInstance(DSASet.MD5).verify(h, new ArrayList<String>(){
            {
                add(message);
            }
        }, TEST_KEY);
        System.out.println(valid);
    }

    @After
    public void tearDown(){

    }
}
