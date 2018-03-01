package com.leibangzhu.starters.auth.sign;

public interface ISignSecretProvider {
    // Get secret by appName
    String get(String appName);
}
