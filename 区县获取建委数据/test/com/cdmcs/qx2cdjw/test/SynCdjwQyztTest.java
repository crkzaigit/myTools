package com.cdmcs.qx2cdjw.test;

import com.cdmcs.qx2cdjw.bean.ColumnBean;
import com.cdmcs.qx2cdjw.inf.DynamicBind;
import com.cdmcs.qx2cdjw.service.SynCdjwQyzt;
import com.cdmcs.qx2cdjw.service.SynData2Table;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 同步成都建委企业主体
 * Created by cdmcs on 2015/3/28.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext_test.xml"})
public class SynCdjwQyztTest {
    @Autowired
    SynCdjwQyzt synCdjwQyzt;

    @Test
    public void test1() throws Exception{
        long begin = System.currentTimeMillis();
        synCdjwQyzt.dljg(-1,200,"");
        System.out.println("cost:"+ (System.currentTimeMillis() - begin));
    }

    @Test
    public void teset2() throws Exception{
        long begin = System.currentTimeMillis();
        synCdjwQyzt.ztxx(-1,100,"201904815");
        System.out.println("cost:"+ (System.currentTimeMillis() - begin));
    }
}
