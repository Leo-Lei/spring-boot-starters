package com.leibangzhu.starters.auth.login;

import com.leibangzhu.starters.auth.Principal;
import com.leibangzhu.starters.common.Result;

public interface IUsernamePasswordChecker {
    Result<Principal> check(String username, String password);
}
