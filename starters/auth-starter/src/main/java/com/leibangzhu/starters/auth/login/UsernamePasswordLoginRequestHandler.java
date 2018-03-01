package com.leibangzhu.starters.auth.login;

import com.leibangzhu.starters.common.Result;
import org.springframework.beans.factory.annotation.Autowired;

public class UsernamePasswordLoginRequestHandler implements ILoginRequestHandler {

    @Autowired
    private IUsernamePasswordChecker checker;

    @Override
    public Result handle(LoginRequest request) {
        String userName = request.getParam(LoginRequest.USER_NAME);
        String password = request.getParam(LoginRequest.PASSWORD);

        return checker.check(userName,password);
    }
}
