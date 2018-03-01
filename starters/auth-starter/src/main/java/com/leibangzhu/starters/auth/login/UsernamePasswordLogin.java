package com.leibangzhu.starters.auth.login;

import com.leibangzhu.starters.auth.Principal;
import com.leibangzhu.starters.auth.token.ITokenCreator;
import com.leibangzhu.starters.common.Result;
import org.springframework.beans.factory.annotation.Autowired;

public class UsernamePasswordLogin {

    @Autowired
    private IUsernamePasswordChecker checker;
    @Autowired
    private ITokenCreator tokenCreator;

    public Result<String> doLogin(String userName,String password){
        Result<Principal> result = checker.check(userName,password);
        if (!result.isSuccess()){
            return Result.fail("用户名密码不正确");
        }

        // 创建一个token，并返回
        String token = tokenCreator.create(result.getResult());
        return Result.success(token);
    }
}
