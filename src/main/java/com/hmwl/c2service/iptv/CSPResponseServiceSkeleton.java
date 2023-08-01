/**
 * CSPResponseServiceSkeleton.java
 * <p>
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.7.9  Built on : Nov 16, 2018 (12:05:37 GMT)
 */
package com.hmwl.c2service.iptv;

/**
 *  CSPResponseServiceSkeleton java skeleton for the axisService
 */
public class CSPResponseServiceSkeleton
        implements CSPResponseServiceSkeletonInterface {
    /**
     * Auto generated method signature
     *
     * @param resultNotify0
     * @return resultNotifyResponse1
     */
    public ResultNotifyResponse resultNotify(
            ResultNotify resultNotify0) {
        // todo 这里写主动回调的逻辑
        ResultNotifyResponse resultNotifyResponse = new ResultNotifyResponse();
        CSPResult cspResult = new CSPResult();
        org.apache.axis2.databinding.types.soapencoding.String errorDescription = new org.apache.axis2.databinding.types.soapencoding.String();
        errorDescription.setString("success");
        cspResult.setErrorDescription(errorDescription);
        cspResult.setResult(0);
        resultNotifyResponse.setResultNotifyReturn(cspResult);
        return resultNotifyResponse;
    }
}
