package com.leibangzhu.starters.auth.token;

import com.leibangzhu.starters.auth.Principal;
import com.leibangzhu.starters.common.Result;

import javax.servlet.http.HttpServletRequest;

public interface ITokenChecker {
    Result<Principal> check(HttpServletRequest request);
}
