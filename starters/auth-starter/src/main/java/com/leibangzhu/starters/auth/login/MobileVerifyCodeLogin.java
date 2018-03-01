package com.leibangzhu.starters.auth.login;

import com.leibangzhu.starters.auth.Principal;
import com.leibangzhu.starters.auth.token.ITokenCreator;
import com.leibangzhu.starters.common.Result;
import org.springframework.beans.factory.annotation.Autowired;

public class MobileVerifyCodeLogin {

    @Autowired
    private IMobileVerifyCodeChecker checker;
    @Autowired
    private ITokenCreator tokenCreator;

    public Result<String> doLogin(String mobile,String verifyCode){
        Result<Principal> result = checker.check(mobile,verifyCode);
        if (!result.isSuccess()){
            return Result.fail("验证码不正确");
        }

        // 创建一个token，并返回
        String token = tokenCreator.create(result.getResult());
        return Result.success(token);
    }
}
