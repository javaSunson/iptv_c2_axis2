package com.hmwl.c2service.utils;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.axis2.AxisFault;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @description:
 * @author: sunso
 * @email:
 * @date: 2023/7/27 17:32
 */
@Slf4j
public class WebserviceClient {
    public static void main(String[] args) {
        ctms(); // 测试调用
//        ctmsResp(); 测试回调
    }

    public static void ctmsResp() {
        String url = "http://ip:9999/services/ctmsResp"; // webservice接口地址,不带?wsdl
        String targetNamespace = "iptv"; // 接口文档的targetNamespace
        String method = "resultNotify"; // webservice方法名

        try {
            Map<String, String> paramsMap = new LinkedHashMap<>();
            paramsMap.put("CSPID", "HMWL69");
            paramsMap.put("LSPID", "HMWL56");
            paramsMap.put("CorrelateID", "df51a174ac2f8006e738b3ac61858abc");
            paramsMap.put("CmdResult", "0");
            paramsMap.put("ResultFileURL", "ftp://username:password@01@ip:21/c2/xml/callback/callback.xml");
            String result = Axis2ClientUtils.invokeAxis2Server(url, targetNamespace, method, paramsMap);
            log.info(result);
        } catch (AxisFault ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void ctms() {
        String url = "http://ip:9999/services/ctms"; // webservice接口地址,不带?wsdl
        String targetNamespace = "iptv"; // 接口文档的targetNamespace
        String method = "ExecCmd"; // webservice方法名

        try {
            Map<String, String> paramsMap = new LinkedHashMap<>();
            paramsMap.put("CSPID", "HMWL181");
            paramsMap.put("LSPID", "HMWL69");
            paramsMap.put("CorrelateID", "df51a174ac2f8006e738b3ac61850001");
            paramsMap.put("CmdFileURL", "ftp://ftpuser:password@01@ip:21/41e13cad5f2f961d1e96019de58d4879.xml");
            String result = Axis2ClientUtils.invokeAxis2Server(url, targetNamespace, method, paramsMap);
            log.info(result);
            Map<String, Object> parse = ParseXmlUtils.parse(result);
            log.info(JSON.toJSONString(parse));
        } catch (AxisFault ex) {
            throw new RuntimeException(ex);
        }
    }
}
