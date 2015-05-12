package com.cdmcs.qx2cdjw.test;

import com.cdmcs.qx2cdjw.bean.ColumnBean;
import com.cdmcs.qx2cdjw.inf.CustomSetData;
import com.cdmcs.qx2cdjw.inf.DynamicBind;
import com.cdmcs.qx2cdjw.service.SynData2Table;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Timestamp;
import java.util.*;

/**
 * Created by cdmcs on 2015/3/28.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext_test.xml"})
public class SynData2TableTest {
    @Autowired
    SynData2Table sysData2Table;

    @Test
    public void test1() throws Exception{
        String jkxlh = "0E26E300A848472CE054001517D1811A036291CAB34CB2103E893EC9F4CFD54F";
        String jkid = "A00001";
        Map<String,String> param = new HashMap<String, String>();
        param.put("PN_PAGE","1");
        param.put("PN_PAGEROWS","10");
        param.put("PC_ZZJGDM","");

        sysData2Table.saveZtxx2Table("YWXT_USER","T_BMBZJ_YW_BZJSQ","C_JFBH",jkid,jkxlh,param,new DynamicBind() {
            public List<List<ColumnBean>> process(List<ColumnBean> columns, Object data){
                List<List<ColumnBean>> ret = new ArrayList<List<ColumnBean>>();
                List<ColumnBean> item = new ArrayList<ColumnBean>();
                for(ColumnBean row : columns){
                    ColumnBean localRow = row.copy();

                   if("C_JFBH".equalsIgnoreCase(localRow.getColumn_name())){
                       localRow.setValue("2015666");
                   }
                   if("D_JFSJ".equalsIgnoreCase(localRow.getColumn_name())){
                       localRow.setValue(new Timestamp(new Date().getTime()));
                   }
                   item.add(localRow);
                }
                List<ColumnBean> item2 = new ArrayList<ColumnBean>();
                for(ColumnBean row : columns){
                    ColumnBean localRow = row.copy();
                    if("C_JFBH".equalsIgnoreCase(localRow.getColumn_name())){
                        localRow.setValue("2015777");
                    }
                    if("D_JFSJ".equalsIgnoreCase(localRow.getColumn_name())){
                        localRow.setValue(new Timestamp(new Date().getTime()));
                    }
                    item2.add(localRow);
                }
                ret.add(item);
                ret.add(item2);
                return ret;
            }

            public <T> T getExtData(Class<T> requiredType) {
                return null;
            }
        });
    }
    @Test
    public void test2() throws Exception{
        this.sysData2Table.save2Table("YWXT_USER","T_BMBZJ_YW_BZJSQ","C_JFBH",new CustomSetData() {
            public Object setData() throws Exception {
                return "aaa";
            }
        },new DynamicBind() {
            public List<List<ColumnBean>> process(List<ColumnBean> columns, Object data) {
                return null;
            }

            public <T> T getExtData(Class<T> requiredType) {
                return null;
            }
        });
    }
}
