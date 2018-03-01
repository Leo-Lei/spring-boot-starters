package com.leibangzhu.starters.dubbo.config;


import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.ProviderConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.spring.AnnotationBean;
import com.alibaba.dubbo.rpc.Exporter;
import com.leibangzhu.starters.common.util.StringUtil;
import com.leibangzhu.starters.dubbo.properties.DubboApplication;
import com.leibangzhu.starters.dubbo.properties.DubboProtocol;
import com.leibangzhu.starters.dubbo.properties.DubboProvider;
import com.leibangzhu.starters.dubbo.properties.DubboRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import javax.naming.ConfigurationException;

@Configuration
@ImportResource(locations={"classpath*:dubbo-provider.xml","classpath*:dubbo-consumer.xml"})
@ConditionalOnClass(Exporter.class)
@EnableConfigurationProperties({DubboApplication.class, DubboProtocol.class, DubboRegistry.class, DubboProvider.class})
public class DubboAutoConfiguration {

    @Autowired
    private DubboApplication dubboApplication;

    @Autowired
    private DubboProtocol dubboProtocol;

    @Autowired
    private DubboProvider dubboProvider;

    @Autowired
    private DubboRegistry dubboRegistry;

    @Bean
    public static AnnotationBean annotationBean(@Value("${dubbo.annotation.package}") String packageName){
        AnnotationBean annotationBean = new AnnotationBean();
        annotationBean.setPackage(packageName);
        System.out.println("[DubboAutoConfiguration] : " + packageName);
        return annotationBean;
    }

    @Bean
    public ApplicationConfig applicationConfig(){
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName(dubboApplication.getName());
        applicationConfig.setLogger(dubboApplication.getLogger());
        return applicationConfig;
    }

    @Bean
    public ProtocolConfig protocolConfig() throws ConfigurationException {
        ProtocolConfig protocolConfig = new ProtocolConfig();
        protocolConfig.setName(dubboProtocol.getName());

        if(-1 == dubboProtocol.getPort()){

            throw new ConfigurationException("dubbo.protocol.port is required in properties file.");
        }

        protocolConfig.setPort(dubboProtocol.getPort());
        protocolConfig.setAccesslog(String.valueOf(dubboProtocol.isAccessLog()));
        protocolConfig.setSerialization(dubboProtocol.getSerialization());
        System.out.println("[DubboAutoConfiguration] : " + dubboProtocol);
        return protocolConfig;
    }

    @Bean
    public ProviderConfig providerConfig(ApplicationConfig applicationConfig, RegistryConfig registryConfig,ProtocolConfig protocolConfig){
        ProviderConfig providerConfig = new ProviderConfig();
        providerConfig.setTimeout(dubboProvider.getTimeout());
        providerConfig.setRetries(dubboProvider.getRetries());
        providerConfig.setDelay(dubboProvider.getDelay());
        providerConfig.setApplication(applicationConfig);
        providerConfig.setRegistry(registryConfig);
        providerConfig.setProtocol(protocolConfig);
        System.out.println("[DubboAutoConfiguration] : " + dubboProvider);
        return providerConfig;
    }

    @Bean
    public RegistryConfig registryConfig(@Value("${data.dir:}") String dataDir) {

        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setProtocol(dubboRegistry.getProtocol());
        registryConfig.setAddress(dubboRegistry.getAddress());
        registryConfig.setRegister(dubboRegistry.isRegister());
        registryConfig.setSubscribe(dubboRegistry.isSubscribe());
        registryConfig.setClient("curator");

        if (StringUtil.isNotBlank(dataDir)){
            registryConfig.setFile(dataDir+"/" + ".dubbo");
        }
        System.out.println(registryConfig.getFile());

        System.out.println("[DubboAutoConfiguration] : " + dubboRegistry);
        return registryConfig;
    }

}
