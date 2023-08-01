# iptv_c2_axis2
IPTV领域C2接口axis2实现版本

 **前言:**

名词解释: IPTVC2, 全称: 央视国际节目定价发布接口规范,标准版本当前最新为2.7.12

附赠资源链接，侵删:[规范](https://download.csdn.net/download/qq_26834611/88142911)

规范中提供的样例，实现基于axis1.4(2006的时代宠物)

基于axis1版本的实现参考: [Spring boot 集成Axis1.4 ，使用wsdd文件发布webservice_董洪臣的博客-CSDN博客](https://blog.csdn.net/dong945221578/article/details/71429735?spm=1001.2014.3001.5502)

 前辈的参考代码: [GitHub - donghc/demo: Spring boot 集成Axis1.4 ，使用wsdd文件发布webservice的demo](https://github.com/donghc/demo)

博主在重构业务代码(基于axis1)的时候，基于SpringBoot2实现，就参考了董老师的代码。



免责条款:

(1) 本着反哺行业，避免走弯路，故编写本文章且提供demo样例。

(2) 所使用示例代码不包含业务代码，且尽可能与公司使用代码区别，严格遵守脱敏。

鸣谢: [Springboot集成Axis2——通过wsdl生成webService_axis2根据wsdl生成services.xml_alistair_chow的博客-CSDN博客](https://blog.csdn.net/alistair_chow/article/details/89556054)

背景介绍
 c2需要通过WebService进行消息交互，并且文档中规定了wsdl格式。由于目前Springboot对cxf框架支持较好，并没对axis进行较好的集成，但是客户放所规定的wsdl又使用到了仅axis支持的rpc模式，因此不得不使用axis作为Webservice框架进行服务的服务端和客户端的搭建。

**实操步骤(axis2实现):**


 Axis2提供了wsdl2java的工具包，首先需要现在Axis2至本地目录（不用是项目目录）。官网下载地址: http://archive.apache.org/dist/axis/axis2/java/core/

可以选择任意版本。最好和要引入的版本一致。当前最新版本为1.8.2，这里使用1.7.9 进行处理。

![img](https://img-blog.csdnimg.cn/d2f7133945144880b5658c5fa066a7c1.png)![点击并拖拽以移动](data:image/gif;base64,R0lGODlhAQABAPABAP///wAAACH5BAEKAAAALAAAAAABAAEAAAICRAEAOw==)编辑

(1) 下载工具包到本地路径，如 D:/tmp,并且解压

![img](https://img-blog.csdnimg.cn/a7a613b7047b4678bd0e647a727c62da.png)![点击并拖拽以移动](data:image/gif;base64,R0lGODlhAQABAPABAP///wAAACH5BAEKAAAALAAAAAABAAEAAAICRAEAOw==)编辑

windows 下执行:

打开cmd

cd到D:/tmp

./wsdl2java.bat -uri ctms.wsdl -d adb -s -ss -sd -ssi -o D:/tmp/ws/server/ctms

这里的ctms结构:

```XML
<?xml version="1.0" encoding="UTF-8"?>
<wsdl:definitions targetNamespace="iptv" xmlns:impl="iptv" xmlns:intf="iptv" xmlns:apachesoap="http://xml.apache.org/xml-soap" xmlns:wsdlsoap="http://schemas.xmlsoap.org/wsdl/soap/" xmlns:soapenc="http://schemas.xmlsoap.org/soap/encoding/" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">
 <wsdl:types>
  <schema xmlns="http://www.w3.org/2001/XMLSchema" targetNamespace="iptv">
   <import namespace="http://schemas.xmlsoap.org/soap/encoding/"/>
   <complexType name="CSPResult">
    <sequence>
     <element name="Result" type="xsd:int"/>
     <element name="ErrorDescription" nillable="true" type="soapenc:string"/>
    </sequence>
   </complexType>
  </schema>
 </wsdl:types>

   <wsdl:message name="ExecCmdRequest">
      <wsdl:part name="CSPID" type="soapenc:string"/>
      <wsdl:part name="LSPID" type="soapenc:string"/>
      <wsdl:part name="CorrelateID" type="soapenc:string"/>
      <wsdl:part name="CmdFileURL" type="soapenc:string"/>
   </wsdl:message>

   <wsdl:message name="ExecCmdResponse">
      <wsdl:part name="ExecCmdReturn" type="impl:CSPResult"/>
   </wsdl:message>

   <wsdl:portType name="CSPRequest">
      <wsdl:operation name="ExecCmd" parameterOrder="CSPID LSPID CorrelateID CmdFileURL">
         <wsdl:input name="ExecCmdRequest" message="impl:ExecCmdRequest"/>
         <wsdl:output name="ExecCmdResponse" message="impl:ExecCmdResponse"/>
      </wsdl:operation>
   </wsdl:portType>

   <wsdl:binding name="ctmsSoapBinding" type="impl:CSPRequest">
      <wsdlsoap:binding style="rpc" transport="http://schemas.xmlsoap.org/soap/http"/>
      <wsdl:operation name="ExecCmd">
         <wsdlsoap:operation soapAction=""/>
         <wsdl:input name="ExecCmdRequest">
            <wsdlsoap:body use="encoded" encodingStyle="http://schemas.xmlsoap.org/soap/encoding/" namespace="iptv"/>
         </wsdl:input>
         <wsdl:output name="ExecCmdResponse">
            <wsdlsoap:body use="encoded" encodingStyle="http://schemas.xmlsoap.org/soap/encoding/" namespace="iptv"/>
         </wsdl:output>
      </wsdl:operation>
   </wsdl:binding>

   <wsdl:service name="CSPRequestService">
      <wsdl:port name="ctms" binding="impl:ctmsSoapBinding">
         <wsdlsoap:address location="http://127.0.0.1"/>
      </wsdl:port>
   </wsdl:service>

</wsdl:definitions>
```

![点击并拖拽以移动](data:image/gif;base64,R0lGODlhAQABAPABAP///wAAACH5BAEKAAAALAAAAAABAAEAAAICRAEAOw==)

这里的ctms.wsdl 是你的soap说明文档，这里可以使用本地绝对路径，或者网络地址。自定替换。

备注: axis2有两种实体类映射形式，一种是adb,一种是xmlsBean这里使用前者，区别自行百度补充。

生成好的文件结构

![img](https://img-blog.csdnimg.cn/225989da50ce4d1b8f7cf65ae0912eb2.png)![点击并拖拽以移动](data:image/gif;base64,R0lGODlhAQABAPABAP///wAAACH5BAEKAAAALAAAAAABAAEAAAICRAEAOw==)编辑

resources:

![img](https://img-blog.csdnimg.cn/5d9278b92cb34fcc9919f878fd6730df.png)![点击并拖拽以移动](data:image/gif;base64,R0lGODlhAQABAPABAP///wAAACH5BAEKAAAALAAAAAABAAEAAAICRAEAOw==)编辑 java:

![img](https://img-blog.csdnimg.cn/a06b438f44a040ccbe3435a2f0f1c374.png)![点击并拖拽以移动](data:image/gif;base64,R0lGODlhAQABAPABAP///wAAACH5BAEKAAAALAAAAAABAAEAAAICRAEAOw==)编辑

 新建或者使用您既有的业务代码，将生成好的代码贴进去，并且修改相关的路径。

如该结构(截图为ctmsResp也进行生成，粘贴后的样子，可以根据需要进一步调整层次):

![img](https://img-blog.csdnimg.cn/821d7d6c3cce4dcfa2106fff6a1ffef4.png)![点击并拖拽以移动](data:image/gif;base64,R0lGODlhAQABAPABAP///wAAACH5BAEKAAAALAAAAAABAAEAAAICRAEAOw==)编辑

 引入pom依赖(仅axis2部分,其他自行引入):

```XML
        <!-- axis2 -->
        <dependency>
            <groupId>org.apache.axis2</groupId>
            <artifactId>axis2-transport-http</artifactId>
            <version>${axis2.version}</version>
            <exclusions>
                <exclusion>
                    <groupId>org.apache.axis2</groupId>
                    <artifactId>axis2-kernel</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
        <dependency>
            <groupId>org.apache.axis2</groupId>
            <artifactId>axis2-adb</artifactId>
            <version>${axis2.version}</version>
        </dependency>
        <dependency>
            <groupId>org.apache.axis2</groupId>
            <artifactId>axis2-transport-local</artifactId>
            <version>${axis2.version}</version>
        </dependency>
        <dependency>
            <groupId>org.apache.axis2</groupId>
            <artifactId>axis2-xmlbeans</artifactId>
            <version>${axis2.version}</version>
        </dependency>
        <dependency>
            <groupId>org.apache.axis2</groupId>
            <artifactId>axis2-jaxws</artifactId>
            <version>${axis2.version}</version>
        </dependency>
        <!-- axis2 -->
```

![点击并拖拽以移动](data:image/gif;base64,R0lGODlhAQABAPABAP///wAAACH5BAEKAAAALAAAAAABAAEAAAICRAEAOw==)

servlet注册(注意，注册路径要和services.xml所在路径一致):

```java
package com.hmwl.c2service;

import com.hmwl.c2service.utils.FileCopyUtils;
import org.apache.axis2.transport.http.AxisServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * axis2配置类，用于设置AxisServlet和访问读取services.xml文件
 *
 */
@Configuration
public class Axis2WebServiceConfiguration {

    //服务访问前缀
    public static final String URL_PATH = "/services/*";
    //services.xml文件的位置
    public static final String SERVICES_FILE_PATH = "WEB-INF/services/axis2/META-INF/services.xml";
    //AXIS2参数key
    public static final String AXIS2_REP_PATH = "axis2.repository.path";

    @Bean
    public ServletRegistrationBean axis2Servlet() {
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean();
        servletRegistrationBean.setServlet(new AxisServlet());
        servletRegistrationBean.addUrlMappings(URL_PATH);
        // 通过默认路径无法找到services.xml，这里需要指定一下路径，且必须是绝对路径
        String path = this.getClass().getResource("/WEB-INF").getPath().toString();
        if (path.toLowerCase().startsWith("file:")) {
            path = path.substring(5);
        }
        if (path.indexOf("!") != -1) {
            try {
                FileCopyUtils.copy(SERVICES_FILE_PATH);
            } catch (IOException e) {
                e.printStackTrace();
            }
            path = path.substring(0, path.lastIndexOf("/", path.indexOf("!"))) + "/WEB-INF";
        }
        //System.out.println("xml配置文件,path={ "+path+" }");
        servletRegistrationBean.addInitParameter(AXIS2_REP_PATH, path);
        servletRegistrationBean.setLoadOnStartup(1);
        return servletRegistrationBean;
    }

}
```

![点击并拖拽以移动](data:image/gif;base64,R0lGODlhAQABAPABAP///wAAACH5BAEKAAAALAAAAAABAAEAAAICRAEAOw==)

业务实现: 

com.hmwl.c2service.iptv.CSPRequestServiceSkeleton

com.hmwl.c2service.iptv.CSPResponseServiceSkeleton

发布接口:

```XML
<?xml version="1.0" encoding="UTF-8"?>
<!-- This file was auto-generated from WSDL -->
<!-- by the Apache Axis2 version: 1.7.9  Built on : Nov 16, 2018 (12:05:37 GMT) -->
<serviceGroup>
    <service name="ctms">
        <messageReceivers>
            <messageReceiver mep="http://www.w3.org/ns/wsdl/in-out" class="com.hmwl.c2service.iptv.CSPRequestServiceMessageReceiverInOut"/>
        </messageReceivers>
        <parameter name="ServiceClass">com.hmwl.c2service.iptv.CSPRequestServiceSkeleton</parameter>
        <parameter name="useOriginalwsdl">false</parameter>
        <parameter name="modifyUserWSDLPortAddress">true</parameter>
        <operation name="ExecCmd" mep="http://www.w3.org/ns/wsdl/in-out" namespace="iptv">
            <actionMapping>iptv/CSPRequest/ExecCmdRequest</actionMapping>
            <outputActionMapping>iptv/CSPRequest/ExecCmdResponse</outputActionMapping>
        </operation>
    </service>
    <service name="ctmsResp">
        <messageReceivers>
            <messageReceiver mep="http://www.w3.org/ns/wsdl/in-out" class="com.hmwl.c2service.iptv.CSPResponseServiceMessageReceiverInOut"/>
        </messageReceivers>
        <parameter name="ServiceClass">com.hmwl.c2service.iptv.CSPResponseServiceSkeleton</parameter>
        <parameter name="useOriginalwsdl">false</parameter>
        <parameter name="modifyUserWSDLPortAddress">true</parameter>
        <operation name="ResultNotify" mep="http://www.w3.org/ns/wsdl/in-out" namespace="iptv">
            <actionMapping>iptv/CSPResponse/resultNotifyRequest</actionMapping>
            <outputActionMapping>iptv/CSPResponse/resultNotifyResponse</outputActionMapping>
        </operation>
    </service>
</serviceGroup>
```

![点击并拖拽以移动](data:image/gif;base64,R0lGODlhAQABAPABAP///wAAACH5BAEKAAAALAAAAAABAAEAAAICRAEAOw==)

测试:

![img](https://img-blog.csdnimg.cn/9b438fa9e2aa416b901fdeb0f831e903.png)![点击并拖拽以移动](data:image/gif;base64,R0lGODlhAQABAPABAP///wAAACH5BAEKAAAALAAAAAABAAEAAAICRAEAOw==)编辑



可能遇到的问题:

(1) 使用axis2调用axis1,会遇到multiRef问题。可以通过重写ServiceClient实现,参考:

com.hmwl.c2service.utils.MyServiceClient

![img](https://img-blog.csdnimg.cn/5201e72bad714ad28aae4a1157585b7b.png)![点击并拖拽以移动](data:image/gif;base64,R0lGODlhAQABAPABAP///wAAACH5BAEKAAAALAAAAAABAAEAAAICRAEAOw==)编辑

原本的实现在getBody后会getFirstElement()，则无法获取到ref标签。

这里整体返回回去后，可以通过自定义解析dom去处理。

如下文:

```XML
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
   <soapenv:Body>
      <ns1:resultNotifyResponse soapenv:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/" xmlns:ns1="http://ctmsResp.c2.webservice.web.external.arcvideo.com">
         <resultNotifyReturn href="#id0"/>
      </ns1:resultNotifyResponse>
      <multiRef id="id0" soapenc:root="0" soapenv:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/" xsi:type="ns2:CSPResult" xmlns:soapenc="http://schemas.xmlsoap.org/soap/encoding/" xmlns:ns2="iptv">
         <Result xsi:type="xsd:int">0</Result>
         <ErrorDescription xsi:type="soapenc:string">Success</ErrorDescription>
      </multiRef>
   </soapenv:Body>
</soapenv:Envelope>
```

![点击并拖拽以移动](data:image/gif;base64,R0lGODlhAQABAPABAP///wAAACH5BAEKAAAALAAAAAABAAEAAAICRAEAOw==)

(2) jar包冲突问题。

比如遇到servletApi2.3的冲突。这是由于axis2-spring的jar引发的。后来发现没用上，就整个去掉了。

比如遇到import org.apache.axis2.transport.http.AxisServlet; 引入不了，可以排除下

```XML
        <dependency>
            <groupId>org.apache.axis2</groupId>
            <artifactId>axis2-transport-http</artifactId>
            <version>1.7.9</version>
            <exclusions>
                <exclusion>
                    <groupId>org.apache.axis2</groupId>
                    <artifactId>axis2-kernel</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
```

![点击并拖拽以移动](data:image/gif;base64,R0lGODlhAQABAPABAP///wAAACH5BAEKAAAALAAAAAABAAEAAAICRAEAOw==)

（3）The endpoint reference (EPR) for the Operation not found 

发布的名称和调用的名称不一致

![img](https://img-blog.csdnimg.cn/7d49fb6f84814e1197ac5b399bede6c1.png)![点击并拖拽以移动](data:image/gif;base64,R0lGODlhAQABAPABAP///wAAACH5BAEKAAAALAAAAAABAAEAAAICRAEAOw==)编辑

(4) org.apache.axis2.databinding.ADBException: Unexpected subelement correlateID

工单这个字段大小写调用和服务端不一致。

com.hmwl.c2service.iptv.ExecCmdResponse 或者回调的Response中看一下，这个报错是在生成的代码里出现的。可以把equals修改成忽略大小写。

还有一些其他报错。反正前前后后，踩了不少坑。

如果其他需要axis1 使用axis2替换的朋友们刷到了这个帖子，希望能帮到你。

demo资源分享(免费好了，就不传csdn资源了，大家搬运请注明出处，且不要任何形式收费哦):

[github](https://github.com/javaSunson/iptv_c2_axis2) | [gitee(推荐)](https://gitee.com/sun577586587/iptv_c2_axis2)
