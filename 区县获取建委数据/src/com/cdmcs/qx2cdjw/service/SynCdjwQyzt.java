package com.cdmcs.qx2cdjw.service;

import com.cdmcs.qx2cdjw.bean.ColumnBean;
import com.cdmcs.qx2cdjw.inf.DynamicBind;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 同步成都建委企业主体
 * Created by cdmcs on 2015/3/28.
 */
public class SynCdjwQyzt {
    private static Logger logger = Logger.getLogger(SynCdjwQyzt.class);

    private SynData2Table synData2Table;

    private SynCdjyzxQyJbh synCdjyzxQyJbh;

    private String qyJbxxTableName = "T_BMBZJ_SJJH_QY_JBXX";
    private String qyJbxxTableOwner = "YWXT_USER";

    private String qyZzxxTableName = "T_BMBZJ_SJJH_QY_ZZ";
    private String qyZzxxTableOwner = "YWXT_USER";

    private String dljgTableName = "VW_ZBBA_SYSDW_ZBDL";
    private String dljgTableOwner ="YWXT_USER";

    public void ztxx(int page,int size,String zzjgdm) throws Exception{
        String jkid = "A00001";
        String key = "0E26E300A848472CE054001517D1811A036291CAB34CB2103E893EC9F4CFD54F";
        Map<String,String> args = new HashMap<String, String>();
        args.put("PN_PAGE",String.valueOf(page));
        args.put("PN_PAGEROWS",String.valueOf(size));
        args.put("PC_ZZJGDM",zzjgdm);
        List<Object> keyVal = this.synData2Table.saveZtxx2Table(this.qyJbxxTableOwner,this.qyJbxxTableName,"N_QYID",jkid,key,args,new DynamicBind() {
            public List<List<ColumnBean>> process(List<ColumnBean> columns, Object data) {
                if(data != null){
                    Map<String,Object> json = JSONObject.fromObject(data.toString());
                    if (json.get("SYS_FLAG") != null){
                        if(Integer.valueOf(json.get("SYS_FLAG").toString()) == 0){
                            Map<String,Object> resut = (Map<String,Object>)json.get("P_RESULT");
                            //rstCol row_count
                            List<Map<String,Object>> list = (List<Map<String,Object>>)resut.get("row_value");
                            Map<String,Object> rstCol = (Map<String,Object>)resut.get("rstCol");

                            //K值为本地表的列名，V为远程接口数据中的列名
                            Map<String,String> columnMapping = new HashMap<String, String>();
                            logger.debug("mapping relation：");
                            for(String v : rstCol.values().toArray(new String[0])){
                                logger.debug(v + " -- " + v);
                                columnMapping.put(v,v);
                            }
                            return mapping(columns,list,columnMapping);
                        }else{
                            if(json.get("SYS_ERROR") != null) throw new RuntimeException(json.get("SYS_ERROR").toString());
                        }
                    }
                }
                return null;
            }

            public <T> T getExtData(Class<T> requiredType) {
                return null;
            }
        });
        if(keyVal != null){
            List<Integer> qyidList = new ArrayList<Integer>();
            for(Object obj : keyVal){
                if(obj != null){
                    try{
                        qyidList.add(Integer.valueOf(obj.toString()));
                    }catch (NumberFormatException e){

                    }
                }
            }

            for(int qyid : qyidList)
            if(qyid != 0){
                jkid = "A00002";
                key = "0E3AB823318F0019E054001517D18684710C4769E71EE66F096BE6B12E945D0A";
                args.clear();
                args.put("PN_QYID",String.valueOf(qyid));

                this.synData2Table.saveZtxx2Table(this.qyZzxxTableOwner,this.qyZzxxTableName,"N_QYID",jkid,key,args,new DynamicBind() {
                    public List<List<ColumnBean>> process(List<ColumnBean> columns, Object data) {
                        if(data != null){
                            Map<String,Object> json = JSONObject.fromObject(data.toString());
                            if (json.get("SYS_FLAG") != null){
                                if(Integer.valueOf(json.get("SYS_FLAG").toString()) == 0){
                                    Map<String,Object> resut = (Map<String,Object>)json.get("P_RESULT");
                                    //rstCol row_count
                                    List<Map<String,Object>> list = (List<Map<String,Object>>)resut.get("row_value");
                                    Map<String,Object> rstCol = (Map<String,Object>)resut.get("rstCol");

                                    //K值为本地表的列名，V为远程接口数据中的列名
                                    Map<String,String> columnMapping = new HashMap<String, String>();
                                    logger.debug("mapping relation：");
                                    for(String v : rstCol.values().toArray(new String[0])){
                                        logger.debug(v + " -- " + v);
                                        columnMapping.put(v,v);
                                    }
                                    return mapping(columns,list,columnMapping);
                                }else{
                                    if(json.get("SYS_ERROR") != null) throw new RuntimeException(json.get("SYS_ERROR").toString());
                                }
                            }
                        }
                        return null;
                    }

                    public <T> T getExtData(Class<T> requiredType) {
                        return null;
                    }
                });
                //同步企业银行信息
                synCdjyzxQyJbh.qyyhxx(qyid);
            }
        }
    };

    public void dljg(int page,int size,String zzjgdm) throws Exception{
        String jkid = "A00003";
        String key = "0E4E9A69F9AA1458E054001517D186841C447C2C56D6A3E1C53FC9366C0B3D0A";

        Map<String,String> args = new HashMap<String, String>();
        args.put("PN_PAGE",String.valueOf(page));
        args.put("PN_PAGEROWS",String.valueOf(size));
        args.put("PC_ZZJGDM",zzjgdm);
        this.synData2Table.saveZtxx2Table(this.dljgTableOwner,this.dljgTableName,"C_JGDM",jkid,key,args,new DynamicBind() {
            public List<List<ColumnBean>> process(List<ColumnBean> columns, Object data) {
                if(data != null){
                    Map<String,Object> json = JSONObject.fromObject(data.toString());
                    if (json.get("SYS_FLAG") != null){
                        if(Integer.valueOf(json.get("SYS_FLAG").toString()) == 0){
                            Map<String,Object> resut = (Map<String,Object>)json.get("P_RESULT");
                            //rstCol row_count
                            List<Map<String,Object>> list = (List<Map<String,Object>>)resut.get("row_value");
                            Map<String,Object> rstCol = (Map<String,Object>)resut.get("rstCol");

                            //K值为本地表的列名，V为远程接口数据中的列名
                            Map<String,String> columnMapping = new HashMap<String, String>();
                            logger.debug("mapping relation：");
                            for(String v : rstCol.values().toArray(new String[0])){
                                logger.debug(v + " -- " + v);
                                columnMapping.put(v,v);
                            }
                            return mapping(columns,list,columnMapping);
                        }else{
                            if(json.get("SYS_ERROR") != null) throw new RuntimeException(json.get("SYS_ERROR").toString());
                        }
                    }
                }
                return null;
            }

            public <T> T getExtData(Class<T> requiredType) {
                return null;
            }
        });
    }

    public void setSynData2Table(SynData2Table synData2Table) {
        this.synData2Table = synData2Table;
    }

    /**
     * 映射函数
     * @param columns
     * @param dataList
     * @param columnMapping K值为本地表的列名，V为远程接口数据中的列名
     * @return
     */
    public static List<List<ColumnBean>> mapping(List<ColumnBean> columns,List<Map<String,Object>> dataList,Map<String,String> columnMapping){
        List<List<ColumnBean>> ret = new ArrayList<List<ColumnBean>>() ;
        if(columnMapping != null && columnMapping.size() != 0 && dataList != null && dataList.size() != 0 && columns != null && columns.size() != 0 ){
            for(Map<String,Object> remote_row : dataList){
                List<ColumnBean> local_row = new ArrayList<ColumnBean>();
                for(ColumnBean local_column_bean : columns){
                    String local_key = local_column_bean.getColumn_name();
                    String remote_key = columnMapping.get(local_key);
                    Object remote_val = remote_row.get(remote_key);
                    ColumnBean local_column = getColumnBean(columns,local_key);
                    if(null != local_column){
                        if(remote_val != null && !remote_val.equals("")){
                            if(local_column.getData_type().equalsIgnoreCase("DATE")){
                                try{
                                    long time = Long.valueOf(remote_val.toString()).longValue();
                                    local_column.setValue(new Timestamp(time));
                                }catch (NumberFormatException e){
                                    try{
                                        local_column.setValue(new Timestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(remote_val.toString()).getTime()));
                                    }catch (ParseException e2){
                                        e2.printStackTrace();
                                    }
                                }
                            }else{
                                local_column.setValue(remote_val);
                            }
                        }
                        logger.debug(local_column.getColumn_id()+"="+remote_val);
                        local_row.add(local_column);
                    }
                }
                ret.add(local_row);
            }
        }
        return ret;
    }
    private static ColumnBean getColumnBean(List<ColumnBean> columns,String key){
        for(ColumnBean item : columns){
            if(item.getColumn_name().equalsIgnoreCase(key)){
                return item.copy();
            }
        }
        return null;
    }

    public String getQyJbxxTableName() {
        return qyJbxxTableName;
    }

    public void setQyJbxxTableName(String qyJbxxTableName) {
        this.qyJbxxTableName = qyJbxxTableName;
    }

    public String getQyJbxxTableOwner() {
        return qyJbxxTableOwner;
    }

    public void setQyJbxxTableOwner(String qyJbxxTableOwner) {
        this.qyJbxxTableOwner = qyJbxxTableOwner;
    }

    public String getQyZzxxTableName() {
        return qyZzxxTableName;
    }

    public void setQyZzxxTableName(String qyZzxxTableName) {
        this.qyZzxxTableName = qyZzxxTableName;
    }

    public String getQyZzxxTableOwner() {
        return qyZzxxTableOwner;
    }

    public void setQyZzxxTableOwner(String qyZzxxTableOwner) {
        this.qyZzxxTableOwner = qyZzxxTableOwner;
    }

    public String getDljgTableName() {
        return dljgTableName;
    }

    public void setDljgTableName(String dljgTableName) {
        this.dljgTableName = dljgTableName;
    }

    public String getDljgTableOwner() {
        return dljgTableOwner;
    }

    public void setDljgTableOwner(String dljgTableOwner) {
        this.dljgTableOwner = dljgTableOwner;
    }

    public void setSynCdjyzxQyJbh(SynCdjyzxQyJbh synCdjyzxQyJbh) {
        this.synCdjyzxQyJbh = synCdjyzxQyJbh;
    }
}
