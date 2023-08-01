package com.hmwl.c2service.utils;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: sunso
 * @email:
 * @date: 2023/7/28 13:19
 */

@Slf4j
public class ParseXmlUtils {

    public static Map<String, Object> map = new HashMap<>();

    public static Map<String, Object> parse(String soap) {
        try {

            //报文转成doc对象
            Document doc = DocumentHelper.parseText(soap);
            //获取根元素，准备递归解析这个XML树
            Element root = doc.getRootElement();
            iteratorE(root);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return map;
    }

    public static void iteratorE(Element root) {
        if (root.elements() != null) {
            //若是当前跟节点有子节点，找到子节点
            List<Element> list = root.elements();
            for (Element e : list) {//遍历每一个节点
                if (e.elements().size() > 0) {
                    //当前节点不为空的话，递归遍历子节点；
                    iteratorE(e);
                }
                if (e.elements().size() == 0) {
                    //若是为叶子节点，那么直接把名字和值放入map
                    map.put(e.getName(), e.getTextTrim());
                }
            }
        }
    }

    public static void main(String[] args) throws DocumentException {
        String soap = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><soap:Envelope xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:s=\"http://esb.spdbbiz.com/services/S120030432\" xmlns:d=\"http://esb.spdbbiz.com/metadata\" ><soap:Header><s:ReqHeader><d:Mac>0000000000000000</d:Mac><d:MacOrgId></d:MacOrgId><d:SourceSysId>0825</d:SourceSysId><d:ConsumerId>0825</d:ConsumerId><d:ServiceAdr>http://esb.spdbbiz.com:7701/services/S120030432</d:ServiceAdr><d:ServiceAction>urn:/UnfSocCrdtNoQry</d:ServiceAction><d:ExtendContent>00</d:ExtendContent></s:ReqHeader></soap:Header><soap:Body><s:ReqUnfSocCrdtNoQry><s:ReqSvcHeader><s:TranDate>20170913</s:TranDate><s:TranTime>162116290</s:TranTime><s:TranTellerNo>00000000</s:TranTellerNo><s:TranSeqNo></s:TranSeqNo><s:ConsumerId>0825</s:ConsumerId><s:GlobalSeqNo>15800001016431580643158064</s:GlobalSeqNo><s:SourceSysId>0825</s:SourceSysId><s:BranchId></s:BranchId><s:TerminalCode>1a</s:TerminalCode><s:CityCode>021</s:CityCode><s:AuthrTellerNo></s:AuthrTellerNo><s:AuthrPwd></s:AuthrPwd><s:AuthrCardFlag></s:AuthrCardFlag><s:AuthrCardNo></s:AuthrCardNo><s:TranCode>DM91</s:TranCode><s:PIN>1234567890123</s:PIN><s:SysOffset1>0000</s:SysOffset1><s:SysOffset2>0000</s:SysOffset2><s:MsgEndFlag>1</s:MsgEndFlag><s:MsgSeqNo>3000</s:MsgSeqNo><s:SubTranCode></s:SubTranCode><s:TranMode></s:TranMode><s:TranSerialNo>0</s:TranSerialNo></s:ReqSvcHeader><s:SvcBody><s:OrgInstId>100000024</s:OrgInstId></s:SvcBody></s:ReqUnfSocCrdtNoQry></soap:Body></soap:Envelope> ";
        Map<String, Object> parse = ParseXmlUtils.parse(soap);
        Object object = parse.get("ConsumerId");
        System.out.println("object  :" + JSON.toJSONString(object));
    }
}
