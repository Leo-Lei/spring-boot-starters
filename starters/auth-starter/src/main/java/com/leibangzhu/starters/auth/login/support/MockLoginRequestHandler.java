package com.leibangzhu.starters.auth.login.support;

import com.leibangzhu.starters.auth.Principal;
import com.leibangzhu.starters.auth.login.LoginRequest;
import com.leibangzhu.starters.auth.login.ILoginRequestHandler;
import com.leibangzhu.starters.common.Result;

public class MockLoginRequestHandler implements ILoginRequestHandler {

    @Override
    public Result handle(LoginRequest request) {
        return Result.success(Principal.newBuilder().userId("userId").build());
    }
}
