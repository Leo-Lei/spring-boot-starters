package com.leibangzhu.starters.auth.login.filter;

import com.leibangzhu.starters.auth.Principal;
import com.leibangzhu.starters.auth.login.LoginRequest;
import com.leibangzhu.starters.auth.login.ILoginRequestHandler;
import com.leibangzhu.starters.common.Result;

public class LoginLoggingFilter implements ILoginRequestHandlerFilter {
    @Override
    public Result handle(ILoginRequestHandler handler, LoginRequest request) {
        System.out.println(request.getParams());
        Result<Principal> result = handler.handle(request);
        return result;
    }
}
