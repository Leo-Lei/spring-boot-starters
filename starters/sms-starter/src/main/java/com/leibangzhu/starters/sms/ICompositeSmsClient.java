package com.leibangzhu.starters.sms;

import java.util.List;

/**
 * Created by reinhard on 24/05/2017.
 */
public interface ICompositeSmsClient {

    List<String> sendMessage(String mobileNumber, String templateKey, String... templateArgs) throws Exception;
    List<String> sendMessage(List<String> mobileNumbers, String templateKey, String... templateArgs) throws Exception;
}
