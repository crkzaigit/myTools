package com.cdmcs.qx2cdjw.test;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.junit.Test;

import javax.wsdl.Types;
import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.ServiceException;
import javax.xml.rpc.encoding.XMLType;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;

/**
 * Created by cdmcs on 2015/4/16.
 */
public class SimpleTest {

    @Test
    public void test1() throws ServiceException, MalformedURLException, RemoteException {
        String endPotint = "http://www.cdjzaq.com:8001/LockCheckWebServicePort";
        Service service = new Service();
        Call call = (Call) service.createCall();
//        call.addParameter("arg0", XMLType.SOAP_STRING,String.class, ParameterMode.IN);
//        call.addParameter("arg1", XMLType.SOAP_STRING,String.class, ParameterMode.IN);
//        call.addParameter("arg2", XMLType.SOAP_STRING,String.class, ParameterMode.IN);
//        call.addParameter("arg3", XMLType.SOAP_STRING,String.class, ParameterMode.IN);
//        call.addParameter("arg4", XMLType.SOAP_STRING,String.class, ParameterMode.IN);
//        call.setReturnType(XMLType.SOAP_STRING);
        call.setTargetEndpointAddress(new URL(endPotint));
        call.setOperationName(new QName("http://webService.lockCheck.tdjx.com/","getCombinateCheck"));
        call.setUseSOAPAction(true);
        call.setSOAPActionURI(endPotint);
        //call.set
        Object ret = call.invoke(new String[]{"b65b097f55f14f4d", "º”√‹À¯", "cms2013", "34021581611429085954168","5DF447CDFBA05CA1F0EA577A3732DDB73F2CD5A4F0AC6AD5"});

        System.out.println(ret);
    }

    @Test
    public void test2() throws Exception {
    }
}
