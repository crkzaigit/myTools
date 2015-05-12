package com.cdmcs.qx2cdjw.test;

import com.cdmcs.qx2cdjw.service.SynCdjyzxQyJbh;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by cdmcs on 2015/4/3.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext_test.xml"})
public class SynCdjyzxQyjbhTest {

    @Autowired
    SynCdjyzxQyJbh synCdjyzxQyJbh;

    @Test
    public void test1(){
        try {
            synCdjyzxQyJbh.qyyhxx(213971);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
