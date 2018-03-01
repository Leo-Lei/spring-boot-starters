package com.leibangzhu.starters.auth.login.filter;

import com.leibangzhu.starters.auth.login.ILoginRequestHandler;
import com.leibangzhu.starters.auth.login.LoginRequest;
import com.leibangzhu.starters.common.Result;

// 该Filter用于在LoginRequestHandler处理登录请求先后，添加额外的逻辑
public interface ILoginRequestHandlerFilter {
    // do something before handler ...
    // Result = handler.handle(request)
    // do something after handler ...
    // return result
    Result handle(ILoginRequestHandler handler, LoginRequest request);
}
