package com.leibangzhu.starters.auth.token;

import com.leibangzhu.starters.common.Result;

import javax.servlet.http.HttpServletRequest;

public interface ITokenCheckerFilter {
    // do something before tokenChecker ...
    // Result = tokenChecker.check(request)
    // do something after tokenChecker ...
    // return result
    Result handle(ITokenChecker tokenChecker, HttpServletRequest request);
}
