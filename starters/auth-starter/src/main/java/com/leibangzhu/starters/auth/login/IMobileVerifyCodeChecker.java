package com.leibangzhu.starters.auth.login;

import com.leibangzhu.starters.auth.Principal;
import com.leibangzhu.starters.common.Result;

public interface IMobileVerifyCodeChecker {
    Result<Principal> check(String mobile, String verifyCode);
}
