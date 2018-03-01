package com.leibangzhu.starters.sms;

import com.leibangzhu.starters.common.util.LeibangzhuLogger;
import com.leibangzhu.starters.http.IHttpBroker;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author: lili
 * Date: 2017/2/22
 * Time: 18:03
 */
public abstract class BaseSmsClient implements ISmsClient, DisposableBean {

    private static final Logger logger = LeibangzhuLogger.create(BaseSmsClient.class);

    @Autowired
    protected IHttpBroker httpBroker;

    @Override
    public void destroy() throws Exception{
        
        releaseResouces();
    }

    private Map<String, String> templates;

    protected void releaseResouces(){
    }

    @Override
    public List<String> sendMessage(String mobileNumber, String templateKey, String... templateArgs) {

        if(true == validate(mobileNumber, templateKey)){

            String message = assembleMessageFromTemplate(templateKey, templateArgs);
            return sendMessage(mobileNumber, message);
        }

        return null;
    }



    @Override
    public List<String> sendMessage(List<String> mobileNumbers, String templateKey, String... templateArgs) throws Exception {

        if(null == mobileNumbers || 0 >= mobileNumbers.size()){

            logger.info("No available mobile numbers.");
            return new ArrayList<>();
        }

        if(false == templates.containsKey(templateKey)){

            logger.error(String.format("Validation fail, wrong template key '%s'.", templateKey));
            return new ArrayList<>();
        }


        List<String> validNumbers = new ArrayList<>();
        for(String number : mobileNumbers){

            if(false == isValidMobileNumber(number)){

                logger.error(String.format("Validation fail, wrong mobile number '%s'.", number));
            }else{

                validNumbers.add(number);
            }
        }

        String message = assembleMessageFromTemplate(templateKey, templateArgs);
        return sendMessage(StringUtils.join(validNumbers, ","), message);
    }

    @Override
    public void bindTemplates(Map<String, String> templates) {

        this.templates = templates;
    }

    protected boolean validate(String mobileNumber, String templateKey) {

        if(false == isValidMobileNumber(mobileNumber)){

            logger.error(String.format("Validation fail, wrong mobile number '%s'.", mobileNumber));
            return false;
        }

        if(false == templates.containsKey(templateKey)){

            logger.error(String.format("Validation fail, wrong template key '%s'.", templateKey));
            return false;
        }

        return true;
    }

    protected boolean isValidMobileNumber(String mobileNumber) {

        Pattern pattern = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$");
        Matcher matcher = pattern.matcher(mobileNumber);
        return matcher.matches();
    }

    protected String assembleMessageFromTemplate(String templateKey, String[] templateArgs) {

        String message = templates.get(templateKey);
        if(null != message && null != templateArgs) {
            for (int index = 0; index < templateArgs.length; index++) {
                message = message.replaceAll("\\{" + (index + 1) + "\\}", templateArgs[index]);
            }
        }

        return message;
    }

    protected abstract List<String> sendMessage(String mobileNumber, String message);
}
