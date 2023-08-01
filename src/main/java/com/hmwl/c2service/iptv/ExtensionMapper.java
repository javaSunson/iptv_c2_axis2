/**
 * ExtensionMapper.java
 * <p>
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.7.9  Built on : Nov 16, 2018 (12:06:07 GMT)
 */
package com.hmwl.c2service.iptv;


/**
 *  ExtensionMapper class
 */
@SuppressWarnings({"unchecked",
        "unused"
})
public class ExtensionMapper {
    public static Object getTypeObject(
            String namespaceURI, String typeName,
            javax.xml.stream.XMLStreamReader reader) throws Exception {
        if ("iptv".equals(namespaceURI) && "CSPResult".equals(typeName)) {
            return CSPResult.Factory.parse(reader);
        }

        throw new org.apache.axis2.databinding.ADBException("Unsupported type " +
                namespaceURI + " " + typeName);
    }
}
