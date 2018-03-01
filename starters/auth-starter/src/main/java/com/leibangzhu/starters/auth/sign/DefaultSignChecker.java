package com.leibangzhu.starters.auth.sign;

import com.leibangzhu.starters.common.Result;
import com.leibangzhu.starters.common.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class DefaultSignChecker implements ISignChecker {

    @Autowired
    private ISignSecretProvider secretProvider;

    @Override
    public Result check(HttpServletRequest request) {
        Map<String,String[]> params = request.getParameterMap();

        // 校验签名，包含：
        // 1. 请求参数是否完整，包括appName，timestamp，sign
        // 2. 判断请求是否过期
        // 3. 判断签名是否正确
        if (!AuthUtil.requestIsValide(request)){
            System.out.println("缺少必要参数");
            return Result.fail("缺少必要参数");
        }
        System.out.println("参数完整");

        if(!AuthUtil.requestIsExpiry(request)){
            System.out.println("请求已过期");
            return Result.fail("请求已过期");
        }
        System.out.println("请求未过期");

        String appName = params.get("appName")[0];
        System.out.println("appName:" + appName);

        String secret = secretProvider.get(appName);
        System.out.println("secret in provider:" + secret);

        if (!AuthUtil.verifySign(request,secret)){
            System.out.println("签名错误");
            return Result.fail("签名错误");
        }
        return Result.success();
    }
}
