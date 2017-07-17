package com.leibangzhu.starters.disconf.config;

import com.baidu.disconf.client.DisconfMgrBean;
import com.baidu.disconf.client.DisconfMgrBeanSecond;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;
import java.util.Properties;

@Configuration
public class DisconfAutoConfiguration {

    @Bean(name = "disconfMgrBean",destroyMethod = "destroy")
    public DisconfMgrBean disconfMgrBean(){
        DisconfMgrBean disconfMgrBean = new DisconfMgrBean();
        System.out.println("scanPackage: " + getScanPackage());
        disconfMgrBean.setScanPackage(getScanPackage());
        return disconfMgrBean;
    }

    @Bean(name = "disconfMgrBean2",initMethod = "init",destroyMethod = "destroy")
    public DisconfMgrBeanSecond disconfMgrBeanSecond(){
        return new DisconfMgrBeanSecond();
    }

    private String getScanPackage() {
        String scanPackage = "";
        Properties properties = new Properties();
        InputStream inputStream = null;

        try {
            inputStream = DisconfAutoConfiguration.class.getResourceAsStream("/application.properties");
            properties.load(inputStream);
            scanPackage  = properties.getProperty(disconfScanPackage).trim();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (inputStream != null){
                try {
                    inputStream.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return scanPackage;
    }

    private final String disconfScanPackage = "spring.disconf.scanPackage";
}
