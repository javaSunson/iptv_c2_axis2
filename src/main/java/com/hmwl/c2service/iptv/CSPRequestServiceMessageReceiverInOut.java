/**
 * CSPRequestServiceMessageReceiverInOut.java
 * <p>
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.7.9  Built on : Nov 16, 2018 (12:05:37 GMT)
 */
package com.hmwl.c2service.iptv;


/**
 * CSPRequestServiceMessageReceiverInOut message receiver
 */
public class CSPRequestServiceMessageReceiverInOut extends org.apache.axis2.receivers.AbstractInOutMessageReceiver {
    public void invokeBusinessLogic(
            org.apache.axis2.context.MessageContext msgContext,
            org.apache.axis2.context.MessageContext newMsgContext)
            throws org.apache.axis2.AxisFault {
        try {
            // get the implementation class for the Web Service
            Object obj = getTheImplementationObject(msgContext);

            CSPRequestServiceSkeletonInterface skel = (CSPRequestServiceSkeletonInterface) obj;

            //Out Envelop
            org.apache.axiom.soap.SOAPEnvelope envelope = null;

            //Find the axisOperation that has been set by the Dispatch phase.
            org.apache.axis2.description.AxisOperation op = msgContext.getOperationContext()
                    .getAxisOperation();

            if (op == null) {
                throw new org.apache.axis2.AxisFault(
                        "Operation is not located, if this is doclit style the SOAP-ACTION should specified via the SOAP Action to use the RawXMLProvider");
            }

            String methodName;

            if ((op.getName() != null) &&
                    ((methodName = org.apache.axis2.util.JavaUtils.xmlNameToJavaIdentifier(
                            op.getName().getLocalPart())) != null)) {
                if ("execCmd".equals(methodName)) {
                    ExecCmdResponse execCmdResponse7 = null;
                    ExecCmd wrappedParam = (ExecCmd) fromOM(msgContext.getEnvelope()
                                    .getBody()
                                    .getFirstElement(),
                            ExecCmd.class);

                    execCmdResponse7 = skel.execCmd(wrappedParam);

                    envelope = toEnvelope(getSOAPFactory(msgContext),
                            execCmdResponse7, false,
                            new javax.xml.namespace.QName("iptv",
                                    "ExecCmdResponse"));
                } else {
                    throw new RuntimeException("method not found");
                }

                newMsgContext.setEnvelope(envelope);
            }
        } catch (Exception e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    //
    private org.apache.axiom.om.OMElement toOM(ExecCmd param,
                                               boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(ExecCmd.MY_QNAME,
                    org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.om.OMElement toOM(ExecCmdResponse param,
                                               boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(ExecCmdResponse.MY_QNAME,
                    org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
            org.apache.axiom.soap.SOAPFactory factory, ExecCmdResponse param,
            boolean optimizeContent, javax.xml.namespace.QName elementQName)
            throws org.apache.axis2.AxisFault {
        try {
            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();

            emptyEnvelope.getBody()
                    .addChild(param.getOMElement(
                            ExecCmdResponse.MY_QNAME, factory));

            return emptyEnvelope;
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private ExecCmdResponse wrapExecCmd() {
        ExecCmdResponse wrappedElement = new ExecCmdResponse();

        return wrappedElement;
    }

    /**
     * get the default envelope
     */
    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
            org.apache.axiom.soap.SOAPFactory factory) {
        return factory.getDefaultEnvelope();
    }

    private Object fromOM(org.apache.axiom.om.OMElement param,
                          Class type) throws org.apache.axis2.AxisFault {
        try {
            if (ExecCmd.class.equals(type)) {
                return ExecCmd.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (ExecCmdResponse.class.equals(type)) {
                return ExecCmdResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }
        } catch (Exception e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }

        return null;
    }

    private org.apache.axis2.AxisFault createAxisFault(Exception e) {
        org.apache.axis2.AxisFault f;
        Throwable cause = e.getCause();

        if (cause != null) {
            f = new org.apache.axis2.AxisFault(e.getMessage(), cause);
        } else {
            f = new org.apache.axis2.AxisFault(e.getMessage());
        }

        return f;
    }
} //end of class
