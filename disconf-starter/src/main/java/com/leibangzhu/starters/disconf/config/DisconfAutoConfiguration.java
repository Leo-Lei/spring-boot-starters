package com.leibangzhu.starters.disconf.config;

import com.baidu.disconf.client.DisconfMgrBean;
import com.baidu.disconf.client.DisconfMgrBeanSecond;
import com.leibangzhu.starters.disconf.properties.DisconfProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Configuration
@EnableConfigurationProperties({DisconfProperties.class})
public class DisconfAutoConfiguration {

    @Autowired
    private DisconfProperties disconfProperties;


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

    private String getScanPackage(){
        String scanPackage = "";
        Properties properties = new Properties();
        InputStream inputStream = DisconfAutoConfiguration.class.getResourceAsStream("/application.properties");

        try {
            properties.load(inputStream);
            scanPackage  = properties.getProperty("spring.disconf.scanPackage").trim();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return scanPackage;
    }

}
