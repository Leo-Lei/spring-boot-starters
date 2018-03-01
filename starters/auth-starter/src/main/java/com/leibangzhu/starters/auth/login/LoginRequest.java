package com.leibangzhu.starters.auth.login;

import java.util.LinkedHashMap;
import java.util.Map;

public class LoginRequest {
    public Map<String, String> getParams() {
        return params;
    }

    private Map<String,String> params = new LinkedHashMap<>();

    public static LoginRequestBuilder newBuilder(){
        return new LoginRequestBuilder();
    }

    public LoginRequest(LoginRequestBuilder builder){
        this.params = builder.params;
    }

    public String getParam(String key){
        return this.params.get(key);
    }

    public static class LoginRequestBuilder{
        private Map<String,String> params = new LinkedHashMap<>();

        public LoginRequestBuilder param(String key,String value){
            this.params.put(key,value);
            return this;
        }

        public LoginRequest build(){
            return new LoginRequest(this);
        }
    }

    public static final String USER_NAME = "userName";
    public static final String PASSWORD = "password";
    public static final String OPEN_ID = "openId";
    public static final String MOBILE = "mobile";
    public static final String VERIFY_CODE = "verifyCode";
}
