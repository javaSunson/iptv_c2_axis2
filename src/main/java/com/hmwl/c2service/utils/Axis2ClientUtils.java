package com.hmwl.c2service.utils;

/**
 * @description:
 * @author: sunso
 * @email:
 * @date: 2023/7/27 17:27
 */

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axiom.soap.SOAPBody;
import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.springframework.util.CollectionUtils;

import java.util.Iterator;
import java.util.Map;

/**
 * axis2调用webservice工具类
 *
 * @author lwx
 * @since 2022-09-16
 */
public class Axis2ClientUtils {
    /**
     * 使用axis2调用webservice
     *
     * @param url             wsdl地址
     * @param targetNamespace soap命名空间
     * @param method          目标方法
     * @param params          [参数名,参数值,参数名1,参数值1,...] 参数集合
     * @return 调用结果
     * @throws AxisFault
     */
    public static String invokeAxis2Server(String url, String targetNamespace, String method, Object... params) throws AxisFault, AxisFault {
        MyServiceClient serviceClient = new MyServiceClient();
        Options options = serviceClient.getOptions();
        // 指定调用WebService的URL
        EndpointReference targetEPR = new EndpointReference(url);
        options.setTo(targetEPR);
        // 确定调用方法
        options.setAction(targetNamespace + method);
//        options.setExceptionToBeThrownOnSOAPFault(false); // 抑制报错
        OMFactory fac = OMAbstractFactory.getOMFactory();
        // 指定命名空间
        OMNamespace omNs = fac.createOMNamespace(targetNamespace, "");
        // 指定方法
        OMElement methodElement = fac.createOMElement(method, omNs);
        //为方法指定参数
        for (int i = 0; i < params.length; i = i + 2) {
            OMElement theRegionCode = fac.createOMElement(String.valueOf(params[i]), omNs);
            theRegionCode.setText(String.valueOf(params[i + 1]));
            methodElement.addChild(theRegionCode);
        }
        methodElement.build();

        //远程调用web服务
        SOAPBody soapBody = serviceClient.sendReceive(methodElement);
        Iterator childElements = soapBody.getChildElements();
        String result = soapBody.toString();
        return result;
    }


    /**
     * 使用axis2调用webservice
     *
     * @param url             wsdl地址
     * @param targetNamespace soap命名空间
     * @param method          目标方法
     * @param params          [参数名,参数值,参数名1,参数值1,...] 参数集合
     * @return 调用结果
     * @throws AxisFault
     */
    public static String invokeAxis2Server(String url, String targetNamespace, String method, Map<String, String> params) throws AxisFault, AxisFault {
        MyServiceClient serviceClient = new MyServiceClient();
        Options options = serviceClient.getOptions();
        // 指定调用WebService的URL
        EndpointReference targetEPR = new EndpointReference(url);
        options.setTo(targetEPR);
        // 确定调用方法
        options.setAction(targetNamespace + method);
//        options.setExceptionToBeThrownOnSOAPFault(false); // 抑制报错
        OMFactory fac = OMAbstractFactory.getOMFactory();
        // 指定命名空间
        OMNamespace omNs = fac.createOMNamespace(targetNamespace, "");
        // 指定方法
        OMElement methodElement = fac.createOMElement(method, omNs);
        //为方法指定参数
        if (!CollectionUtils.isEmpty(params)) {
            params.forEach((k, v) -> {
                OMElement theRegionCode = fac.createOMElement(k, omNs);
                theRegionCode.setText(v);
                methodElement.addChild(theRegionCode);
            });
        }
        methodElement.build();

        //远程调用web服务
        SOAPBody soapBody = serviceClient.sendReceive(methodElement);
        Iterator childElements = soapBody.getChildElements();
        String result = soapBody.toString();
        return result;
    }
}
