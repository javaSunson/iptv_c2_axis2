package com.hmwl.c2service;

/**
 * @description:
 * @author: sunso
 * @email:
 * @date: 2023/7/27 10:09
 */

import com.hmwl.c2service.utils.FileCopyUtils;
import org.apache.axis2.transport.http.AxisServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * axis2配置类，用于设置AxisServlet和访问读取services.xml文件
 *
 * @author gblfy
 * @date 2021-09-25
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
