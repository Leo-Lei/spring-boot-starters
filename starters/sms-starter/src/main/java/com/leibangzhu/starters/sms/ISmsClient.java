package com.leibangzhu.starters.sms;

import java.util.List;
import java.util.Map;

/**
 * Author: lili
 * Date: 2017/2/22
 * Time: 17:46
 */
public interface ISmsClient {

    List<String> sendMessage(String mobileNumber, String templateKey, String... templateArgs) throws Exception;
    List<String> sendMessage(List<String> mobileNumbers, String templateKey, String... templateArgs) throws Exception;
    void bindTemplates(Map<String, String> templates);
}
