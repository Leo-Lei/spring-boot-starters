package com.leibangzhu.starters.auth.login;

import com.leibangzhu.starters.common.Result;

public interface ILoginRequestHandler {
    Result handle(LoginRequest request);
}
