package com.leibangzhu.starters.auth.login;

import com.leibangzhu.starters.auth.Principal;
import com.leibangzhu.starters.common.Result;
import org.springframework.beans.factory.annotation.Autowired;

public class MobileVerifyCodeLoginRequestHandler implements ILoginRequestHandler {

    @Autowired
    private IMobileVerifyCodeChecker checker;

    @Override
    public Result<Principal> handle(LoginRequest request) {
        String mobile = request.getParam(LoginRequest.MOBILE);
        String verifyCode = request.getParam(LoginRequest.VERIFY_CODE);

        return checker.check(mobile,verifyCode);
    }
}
