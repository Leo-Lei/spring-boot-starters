package com.leibangzhu.starters.auth.login.filter;

import com.leibangzhu.starters.auth.Principal;
import com.leibangzhu.starters.auth.login.LoginRequest;
import com.leibangzhu.starters.auth.token.ITokenCreator;
import com.leibangzhu.starters.auth.login.ILoginRequestHandler;
import com.leibangzhu.starters.common.Result;
import org.springframework.beans.factory.annotation.Autowired;

public class OutputTokenLoginHandlerFilter implements ILoginRequestHandlerFilter {

    @Autowired
    private ITokenCreator tokenCreator;

    @Override
    public Result handle(ILoginRequestHandler handler, LoginRequest request) {
        Result<Principal> result = handler.handle(request);
        return Result.success(tokenCreator.create(result.getResult()));
    }
}
