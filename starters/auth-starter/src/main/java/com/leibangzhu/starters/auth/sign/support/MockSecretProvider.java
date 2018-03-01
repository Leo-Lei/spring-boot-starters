package com.leibangzhu.starters.auth.sign.support;

import com.leibangzhu.starters.auth.sign.ISignSecretProvider;

public class MockSecretProvider implements ISignSecretProvider{
    @Override
    public String get(String appName) {
        return "secret";
    }
}
