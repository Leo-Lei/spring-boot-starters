package com.leibangzhu.starters.sms;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.leibangzhu.starters.common.util.LeibangzhuLogger;
import org.slf4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: lili
 * Date: 2017/2/23
 * Time: 9:37
 */
@Component
public class CompositeSmsClient implements InitializingBean, ICompositeSmsClient {

    private static final Logger logger = LeibangzhuLogger.create(CompositeSmsClient.class);

    @Autowired
    private ISmsClientSystemConfigurationProvider systemConfigProvider;

    @Autowired
    private Map<String, ISmsClient> availableClients;

    private Map<String, String> templates;
    private List<ISmsClient> orderedClients;

    //Thread safe
    private Gson gson = new Gson();

    @Override
    public void afterPropertiesSet() throws Exception {

        if (null == availableClients && availableClients.isEmpty()) {

            throw new Exception("No available sms client is defined.");
        }

        orderedClients = new ArrayList<>();
        templates = new HashMap<>();
        for (ISmsClient client : availableClients.values()) {

            client.bindTemplates(templates);
            orderedClients.add(client);
        }

        String latestOrderedClientNames = systemConfigProvider.getOrderedClientNames();
        String[] orderedClientNames = latestOrderedClientNames.split(",");

        ISmsClient currentClient = null;
        List<ISmsClient> updatedOrderedClients = new ArrayList<>();

        for (String clientName : orderedClientNames) {

            currentClient = availableClients.get(clientName);
            if (null != currentClient) {

                currentClient.bindTemplates(templates);
                updatedOrderedClients.add(currentClient);
            } else {

                logger.error(String.format("No client '%s' is found in available clients.", clientName));
            }
        }

        orderedClients = updatedOrderedClients;

        String latestTemplatesJson = systemConfigProvider.getTemplatesJson();
        Map<String, String> updatedTemplates;
        try {

            updatedTemplates = gson.fromJson(latestTemplatesJson, new TypeToken<Map<String, String>>() {
            }.getType());

            templates.putAll(updatedTemplates);
        } catch (JsonSyntaxException e) {

            logger.error(String.format("Error while parsing templates json '%s'.", latestTemplatesJson), e);
        }
    }

    public List<String> sendMessage(String mobileNumber, String templateKey, String... templateArgs) throws Exception {

        List<String> successNumbers = new ArrayList<>();

        int index = 0;
        for (ISmsClient client : orderedClients) {

            index++;
            successNumbers = client.sendMessage(mobileNumber, templateKey, templateArgs);
            if (null != successNumbers &&  0 < successNumbers.size()) {

                logger.info(String.format("Message is sent successfully by #'%s' - client '%s'", index, client.getClass().getName()));
            } else {

                logger.warn(String.format("Fail to send message by #'%s' - client '%s'", index, client.getClass().getName()));
            }
        }

        return successNumbers;
    }

    public List<String> sendMessage(List<String> mobileNumbers, String templateKey, String... templateArgs) throws Exception {

        List<String> preparedNumbers = new ArrayList<>();
        preparedNumbers.addAll(mobileNumbers);
        List<String> successNumbers = null;
        List<String> totalSuccessNumbers = new ArrayList<>();

        int index = 0;
        for (ISmsClient client : orderedClients) {

            index++;
            successNumbers = client.sendMessage(preparedNumbers, templateKey, templateArgs);
            totalSuccessNumbers.addAll(successNumbers);
            excludeSuccessNumbers(successNumbers, preparedNumbers);

            if (0 == preparedNumbers.size()) {

                logger.info(String.format("All Messages are sent successfully by #'%s' - client '%s'", index, client.getClass().getName()));
                return mobileNumbers;
            } else {

                logger.warn(String.format("Fail to send message by #'%s' - client '%s'", index, client.getClass().getName()));
            }
        }

        logger.error(String.format("Fail to send message by all '%s' available Clients", availableClients.size()));
        return totalSuccessNumbers;
    }

    private void excludeSuccessNumbers(List<String> successNumbers, List<String> mobileNumbers) {

        for(String number : successNumbers){

            mobileNumbers.remove(number);
        }
    }
}
