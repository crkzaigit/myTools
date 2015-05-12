package com.cdmcs.cdjw.tyjk.test;

import com.cdmcs.cdjw.tyjk.CdjwInfClientService;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cdmcs on 2015/3/28.
 */
public class CdjwInfClientServiceTest {
    @Test
    public void test1(){
        String jkxlh = "0E26E300A848472CE054001517D1811A036291CAB34CB2103E893EC9F4CFD54F";
        String jkid = "A00001";
        Map<String,String> param = new HashMap<String, String>();
        param.put("PN_PAGE","1");
        param.put("PN_PAGEROWS","10");
        param.put("PC_ZZJGDM","");

        CdjwInfClientService service = new CdjwInfClientService();
        try {
            System.out.println(service.ztxx(jkid,jkxlh,param));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
