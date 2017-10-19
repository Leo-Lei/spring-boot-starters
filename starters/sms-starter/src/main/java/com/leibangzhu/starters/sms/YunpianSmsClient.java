package com.leibangzhu.starters.sms;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.leibangzhu.starters.common.util.QibeiLogger;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: lili
 * Date: 2017/2/22
 * Time: 17:50
 */
public class YunpianSmsClient extends BaseSmsClient {

    private static final Logger logger = QibeiLogger.create(YunpianSmsClient.class);

    private static final String URI_SEND_SMS = "https://sms.yunpian.com/v2/sms/single_send.json";
    private static final String URI_SEND_SMS_BATCH = "https://sms.yunpian.com/v2/sms/batch_send.json";

    private final String apiKey;

    public YunpianSmsClient(String apiKey) {

        this.apiKey = apiKey;
    }

    @Override
    public List<String> sendMessage(String mobileNumber, String message) {

        String responseText = "";
        Map<String, Object> params = prepareRequestParameters(mobileNumber, message);

        boolean isBatchMode = mobileNumber.contains(",");

        if(true == isBatchMode){

            responseText = httpBroker.post(URI_SEND_SMS_BATCH, params, null);
        }else {

            responseText = httpBroker.post(URI_SEND_SMS, params, null);
        }

        List<String> successNumbers = new ArrayList<>();

        if (null != responseText) {

            JSONObject jsonObj = JSON.parseObject(responseText);
            if (true == isBatchMode) {

                JSONArray jsonObjArray = jsonObj.getJSONArray("data");
                for(int index = 0; index < jsonObjArray.size(); index++){

                    parseResponseResult(successNumbers, jsonObjArray.getJSONObject(index));
                }
            } else {

                parseResponseResult(successNumbers, jsonObj);
            }


        } else {

            logger.warn("Empty response from YunPian.");
        }

        return successNumbers;
    }

    private void parseResponseResult(List<String> successNumbers, JSONObject jsonObj) {

        if (0 == jsonObj.getInteger("code")) {

            successNumbers.add(jsonObj.getString("mobile"));
        } else {

            logger.error(
                    String.format(
                            "Fail to send message by YunPian, detail message is '%s', '%s'",
                            jsonObj.getString("msg"),
                            jsonObj.getString("detail")));
        }
    }

    private Map<String, Object> prepareRequestParameters(String mobileNumber, String message) {

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("apikey", apiKey);
        params.put("text", message);
        params.put("mobile", mobileNumber);
        return params;
    }

    @Override
    protected void releaseResouces() {

        super.releaseResouces();
    }
}
