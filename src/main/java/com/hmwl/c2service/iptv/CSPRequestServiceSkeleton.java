/**
 * CSPRequestServiceSkeleton.java
 * <p>
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.7.9  Built on : Nov 16, 2018 (12:05:37 GMT)
 */
package com.hmwl.c2service.iptv;

/**
 *  CSPRequestServiceSkeleton java skeleton for the axisService
 */
public class CSPRequestServiceSkeleton
        implements CSPRequestServiceSkeletonInterface {
    /**
     * Auto generated method signature
     *
     * @param execCmd0
     * @return execCmdResponse1
     */
    public ExecCmdResponse execCmd(ExecCmd execCmd0) {
        // todo 这里写注入的逻辑
        ExecCmdResponse execCmdResponse = new ExecCmdResponse();
        CSPResult cspResult = new CSPResult();
        cspResult.setResult(0);
        org.apache.axis2.databinding.types.soapencoding.String errorDescription = new org.apache.axis2.databinding.types.soapencoding.String();
        errorDescription.setString("success");
        cspResult.setErrorDescription(errorDescription);
        execCmdResponse.setExecCmdReturn(cspResult);
        return execCmdResponse;
    }
}
