package com.leibangzhu.starters.auth.sign.support;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
import com.leibangzhu.starters.auth.sign.ISignSecretProvider;

public class ApolloSecretProvider implements ISignSecretProvider {
    // 通过appName查找对应的secret
    @Override
    public String get(String appName) {
        Config config = ConfigService.getAppConfig();
        String secret = config.getProperty(appName + ".secret","");
        return secret;
    }
}
