package com.leibangzhu.starters.common.resource;

import com.leibangzhu.starters.common.util.QibeiLogger;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * Created by reinhard on 02/08/2017.
 */
public class ResourceManager {

    private static ResourceBundle resourceBundle = null;

    private static final Logger logger = QibeiLogger.create(ResourceManager.class);

    private int code;
    private String responseErrorMessage;

    public static final String DEFAULT_CULTURE = "zh_CN";

    static{

        String localeString = DEFAULT_CULTURE;
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("application.properties"));
        try {
            propertiesFactoryBean.afterPropertiesSet();
            Properties properties = propertiesFactoryBean.getObject();
            localeString = StringUtils.isNoneEmpty(properties.getProperty("resource.culture")) ? properties.getProperty("resource.culture") : DEFAULT_CULTURE;
        } catch (IOException e) {

            //Do Nothing
        }

        resourceBundle = ResourceBundle.getBundle("resources", new Locale(localeString));
    }

    public String getResourceString(String prefix, int code){

        return resourceBundle.getString(String.format("%s_%s", prefix, code));
    }
}
