package com.leibangzhu.starters.auth.sign;

import com.leibangzhu.starters.common.Result;

import javax.servlet.http.HttpServletRequest;

public interface ISignChecker {
    Result check(HttpServletRequest request);

}
