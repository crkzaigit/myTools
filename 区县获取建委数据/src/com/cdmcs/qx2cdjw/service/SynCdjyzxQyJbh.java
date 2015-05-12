package com.cdmcs.qx2cdjw.service;

import com.cdmcs.qx2cdjw.bean.ColumnBean;
import com.cdmcs.qx2cdjw.inf.CustomSetData;
import com.cdmcs.qx2cdjw.inf.DynamicBind;
import net.sf.json.JSONObject;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ͬ���ɶ��н������� ��ҵ������
 * Created by cdmcs on 2015/4/3.
 */
public class SynCdjyzxQyJbh {
    private static Logger logger = Logger.getLogger(SynCdjyzxQyJbh.class);

    private SynData2Table synData2Table;

    private String qyyhxxTableName = "T_BMBZJ_YW_QYYHXX";
    private String qyyhxxTableOwner = "YWXT_USER";

    private String qyyhxxLsbTableName = "T_BMBZJ_YW_QYYHXX_LSSQ";
    private String qyyhxxLsbTableOwner = "YWXT_USER";

    private String endpoint = "http://www.cdggzy.com/services/ztbDataService?wsdl";

    public void qyyhxx(final Integer qyid) throws Exception{
        if(qyid == null || qyid.intValue() == 0){
            return;
        }

        //��̬������
        DynamicBind dynamicBind = new DynamicBind() {
            Map<String,Object> P_RESULT_LSB = null;
            public List<List<ColumnBean>> process(List<ColumnBean> columns, Object data) {
                if(data != null){
                    Map<String,Object> json = JSONObject.fromObject(data.toString());
                    if (json.get("PN_FLAG") != null){
                        if(Integer.valueOf(json.get("PN_FLAG").toString()) == 0){
                            //��ҵ������Ϣ��ˮ��
                            P_RESULT_LSB = (Map<String,Object>)json.get("P_RESULT_LSB");


                            Map<String,Object> resut = (Map<String,Object>)json.get("P_RESULT_JBXX");
                            //rstCol row_count
                            List<Map<String,Object>> list = (List<Map<String,Object>>)resut.get("row_value");
                            Map<String,Object> rstCol = (Map<String,Object>)resut.get("rstCol");

                            //KֵΪ���ر��������VΪԶ�̽ӿ������е�����
                            Map<String,String> columnMapping = new HashMap<String, String>();
                            logger.debug("mapping relation��");
                            for(String v : rstCol.values().toArray(new String[0])){
                                logger.debug(v + " -- " + v);
                                columnMapping.put(v,v);
                            }
                            return SynCdjwQyzt.mapping(columns,list,columnMapping);
                        }else{
                            if(json.get("PC_MSG") != null) throw new RuntimeException(json.get("PC_MSG").toString());
                        }
                    }
                }
                return null;
            }

            public <T> T getExtData(Class<T> requiredType) {
                return (T)P_RESULT_LSB;
            }

        };
        //�����������
        this.synData2Table.save2Table(this.qyyhxxTableOwner,this.qyyhxxTableName,"N_QYID",new CustomSetData() {
            public Object setData() throws Exception {
                Service service = new Service();
                Call call = (Call) service.createCall();
                call.setTargetEndpointAddress(endpoint);// Զ�̵���·��
                call.setOperationName("invoke");// ���õķ�����
                call.addParameter("arg", // ������
                        org.apache.axis.encoding.XMLType.XSD_STRING,// ��������:String
                        javax.xml.rpc.ParameterMode.IN);// ����ģʽ��'IN' or 'OUT'
                // ���÷���ֵ���ͣ�
                call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);// ����ֵ���ͣ�String
                String arg = "{PN_QYID:"+qyid.intValue()+",AJAX_PRIVATE_FUNC_ID:'APPUSER.GGZY_ZTB_INF.PK_QUERY_QYJBHXX',wybs:'80022a2202f6533292d6735ac01e5883'}";
                logger.info("ͬ����ҵ������ JSON_ARGS="+arg);
                String ret = (String) call.invoke(new Object[] { arg });
                logger.info("�ӿڷ���="+ret);
                return ret;// Զ�̵���
            }
        },dynamicBind);

        final Map<String,Object> lsb = dynamicBind.getExtData(Map.class);

        //�����������ˮ��
        this.synData2Table.save2Table(this.qyyhxxLsbTableOwner,this.qyyhxxLsbTableName,"N_QYID",new CustomSetData(){

            public Object setData() throws Exception {
                return lsb;
            }
        },new DynamicBind(){

            public List<List<ColumnBean>> process(List<ColumnBean> columns, Object data) {
                if(data != null){
                    Map<String,Object> resut = (Map<String,Object>)data;
                    //rstCol row_count
                    List<Map<String,Object>> list = (List<Map<String,Object>>)resut.get("row_value");
                    Map<String,Object> rstCol = (Map<String,Object>)resut.get("rstCol");

                    //KֵΪ���ر��������VΪԶ�̽ӿ������е�����
                    Map<String,String> columnMapping = new HashMap<String, String>();
                    logger.debug("mapping relation��");
                    for(String v : rstCol.values().toArray(new String[0])){
                        logger.debug(v + " -- " + v);
                        columnMapping.put(v,v);
                    }
                    return SynCdjwQyzt.mapping(columns,list,columnMapping);
                }
                return null;
            }

            public <T> T getExtData(Class<T> requiredType){
                return null;
            };
        });
    }


    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public void setSynData2Table(SynData2Table synData2Table) {
        this.synData2Table = synData2Table;
    }

    public void setQyyhxxTableName(String qyyhxxTableName) {
        this.qyyhxxTableName = qyyhxxTableName;
    }

    public void setQyyhxxTableOwner(String qyyhxxTableOwner) {
        this.qyyhxxTableOwner = qyyhxxTableOwner;
    }

    public void setQyyhxxLsbTableName(String qyyhxxLsbTableName) {
        this.qyyhxxLsbTableName = qyyhxxLsbTableName;
    }

    public void setQyyhxxLsbTableOwner(String qyyhxxLsbTableOwner) {
        this.qyyhxxLsbTableOwner = qyyhxxLsbTableOwner;
    }
}
