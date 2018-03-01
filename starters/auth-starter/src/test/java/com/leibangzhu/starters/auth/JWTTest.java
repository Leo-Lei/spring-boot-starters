package com.leibangzhu.starters.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.leibangzhu.starters.common.Result;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class JWTTest {

    @Ignore
    @Test
    public void test() throws Exception {
        String token = create();
        Thread.sleep(2 * 1000);
        verify(token);
    }

    private String create() throws Exception {
        Algorithm al = Algorithm.HMAC256("secret123");
        String token = JWT.create()
                .withIssuer("签发者")
                .withSubject("用户")
                .withClaim(Principal.USER_ID,"leiwei")
                .withExpiresAt(new Date(System.currentTimeMillis() + 600 * 1000))
                .sign(al);

        System.out.println(token);
        return token;
    }

    private static void verify(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256("secret123");
            JWTVerifier verifier = JWT.require(algorithm)
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            System.out.println(jwt.getClaim(Principal.USER_ID).asString());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("校验失败");
        }
    }


    @Ignore
    @Test
    public void test001() throws Exception {
        String token = com.leibangzhu.starters.auth.jwt.JWT.creator()
                .secret("secret1234")
                .issuer("签发者")
                .subject("用户")
                .claim(Principal.USER_ID,"leiwei")
                .expiredAt(TimeUnit.HOURS,10)
                .create();

        //System.out.println(token);

        Thread.sleep(2 * 1000);

        Result<DecodedJWT> result = com.leibangzhu.starters.auth.jwt.JWT.verifier()
                .token(token)
                .secret("secret1234")
                .verify();

        System.out.println(result.getResult().getClaim(Principal.USER_ID).asString());
    }

    @Test
    public void test002(){

        Principal user = Principal
                .newBuilder()
                .userId("leiwei")
                .property("role","role1")
                .build();

        String token = com.leibangzhu.starters.auth.jwt.JWT.creator()
                .secret("secret1234")
                .claim(Principal.USER_ID,user.getUserId())
                .claims(user.getProperties())
                .expiredAt(TimeUnit.HOURS,24)
                .create();

        Result<DecodedJWT> result = com.leibangzhu.starters.auth.jwt.JWT.verifier().secret("secret1234").token(token).verify();
        boolean b = result.isSuccess();



    }







}
