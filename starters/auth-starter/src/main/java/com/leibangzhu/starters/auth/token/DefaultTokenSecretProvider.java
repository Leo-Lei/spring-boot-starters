package com.leibangzhu.starters.auth.token;

public class DefaultTokenSecretProvider implements ITokenSecretProvider {
    @Override
    public String get() {
        return "123456";
    }
}
