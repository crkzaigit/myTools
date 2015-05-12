package com.cdmcs.cdjw.tyjk;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.log4j.Logger;
import sun.misc.BASE64Encoder;
import java.net.URL;
import java.util.Map;

/**
 * Created by cdmcs on 2015/3/27.
 */

public class CdjwInfClientService {
    private static Logger logger = Logger.getLogger(CdjwInfClientService.class);

    private String endPotint = null;

    public CdjwInfClientService(){

    }

    public CdjwInfClientService(String endPotint){
        this.endPotint = endPotint;
    }
    public Object invoke(String xllb,String jkid,String key,Map<String,String> args) throws Exception{
        if(xllb == null || xllb.trim().equals("")){
            logger.error("xllb can not be null");
            throw new RuntimeException("xllb can not be null");
        }

        if(jkid == null || jkid.trim().equals("")){
            logger.error("jkid can not be null");
            throw new RuntimeException("jkid can not be null");
        }

        if(key == null || key.trim().equals("")){
            logger.error("key can not be null");
            throw new RuntimeException("key can not be null");
        }
        StringBuilder params = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\"?><root><params>");
        if(args != null && args.size() != 0){
            for(String argKey :args.keySet()){
                String argVal = args.get(argKey);
                if(argVal != null && !argVal.trim().equals("")){
                    params.append("<").append(argKey).append(">");
                    params.append("<![CDATA[").append(argVal).append("]]>");
                    params.append("</").append(argKey).append(">");
                }
            }
        }
        params.append("</params></root>");
        return call_interface(xllb,params.toString(),jkid,key);
    }
    public Object ztxx(String jkid,String key,Map<String,String> args) throws Exception{
        if(jkid == null || jkid.trim().equals("")){
            logger.error("jkid can not be null");
            throw new RuntimeException("jkid can not be null");
        }

        if(key == null || key.trim().equals("")){
            logger.error("key can not be null");
            throw new RuntimeException("key can not be null");
        }

        return invoke("A0",jkid,key,args);
    }


    private Object call_interface(String xllb,String xmlArgs,String jkid,String jkxlh) throws Exception{
        if(endPotint == null){
            logger.error("endPoint can not be null");
            throw new RuntimeException("endPoint can not be null");
        }
        logger.info("endPoint="+this.endPotint);
        logger.info("begin invoke ws: xllb="+xllb+",jkid="+jkid+",jkxlh(key)="+jkxlh+",xmlArgs="+xmlArgs);
        String dataType = "json";
        BASE64Encoder base64Encoder = new BASE64Encoder();
        String xmlDoc64 = base64Encoder.encode(xmlArgs.getBytes("UTF-8"));
        Service service = new Service();
        Call call = (Call) service.createCall();
        call.setTargetEndpointAddress(new URL(endPotint));
        call.setOperationName("callInterfaceByDataType");
        call.setUseSOAPAction(true);
        call.setSOAPActionURI(endPotint);
        Object ret = call.invoke(new Object[]{xllb, jkxlh, jkid, xmlDoc64, dataType});
        if(null != ret){
            logger.info("ws return info="+ret.toString());
        }else{
            logger.info("ws return null");
        }
        return ret;
    };

    public void setEndPotint(String endPotint) {
        this.endPotint = endPotint;
    }
}
