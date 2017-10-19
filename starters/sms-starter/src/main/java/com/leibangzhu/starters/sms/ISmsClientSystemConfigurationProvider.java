package com.leibangzhu.starters.sms;

/**
 * Author: lili
 * Date: 2017/2/23
 * Time: 10:45
 */
public interface ISmsClientSystemConfigurationProvider {

    String getOrderedClientNames();
    String getTemplatesJson();
}
