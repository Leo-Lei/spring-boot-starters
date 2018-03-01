package com.leibangzhu.starters.auth.token;

import com.leibangzhu.starters.auth.Principal;
import com.leibangzhu.starters.auth.jwt.JWT;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.TimeUnit;

public class DefaultTokenCreator implements ITokenCreator {

    @Autowired
    private ITokenSecretProvider secretProvider;

    @Override
    public String create(Principal user) {

        String secret = secretProvider.get();

        return JWT.creator()
                .secret(secret)
                .claim(Principal.USER_ID,user.getUserId())
                .claim(Principal.ROLES, String.join(",",user.getRoles()))
                .claim(Principal.PERMISSIONS,String.join(",",user.getPermissions()))
                .claims(user.getProperties())
                .expiredAt(TimeUnit.HOURS,24)
                .create();
    }
}
