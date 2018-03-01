package com.leibangzhu.starters.auth.jwt;

import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.leibangzhu.starters.common.Result;
import com.leibangzhu.starters.common.util.StringUtil;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

// JWT的工具类,不是一个Spring的Bean。依赖尽量少
public class JWT {

    public static JWTCreator creator(){
        return new JWTCreator();
    }

    public static JWTVerifier verifier(){
        return new JWTVerifier();
    }

    public static class JWTCreator {
        private String issuer;
        private String subject;
        private Map<String,String> claims = new LinkedHashMap<>();
        private int expired = 1;
        private TimeUnit expiredTimeUnit = TimeUnit.HOURS;
        private String secret = "";

        public JWTCreator issuer(String issuer){
            this.issuer = issuer;
            return this;
        }

        public JWTCreator subject(String subject){
            this.subject = subject;
            return this;
        }

        public JWTCreator claim(String key, String value){
            this.claims.put(key,value);
            return this;
        }

        public JWTCreator claims(Map<String,String> claims){
            for (Map.Entry<String,String> entry: claims.entrySet()){
                this.claims.put(entry.getKey(),entry.getValue());
            }
            return this;
        }

        public JWTCreator expiredAt(TimeUnit unit, int value){
            this.expired = value;
            this.expiredTimeUnit = unit;
            return this;
        }

        public JWTCreator secret(String secret){
            this.secret = secret;
            return this;
        }

        public String create() {
            Algorithm al = null;
            try {
                al = Algorithm.HMAC256(secret);
            } catch (Exception e) {
                e.printStackTrace();
            }

            com.auth0.jwt.JWTCreator.Builder builder = com.auth0.jwt.JWT.create();
            if (StringUtil.isNotBlank(issuer)){
                builder.withIssuer(issuer);
            }

            if (StringUtil.isNotBlank(subject)){
                builder.withSubject(subject);
            }

            if (!claims.isEmpty()){
                for (Map.Entry<String,String> entry : claims.entrySet()){
                    builder.withClaim(entry.getKey(),entry.getValue());
                }
            }

            builder.withExpiresAt(new Date(System.currentTimeMillis() + expiredTimeUnit.toMillis(expired)));
            return builder.sign(al);
        }
    }

    public static class JWTVerifier {
        private String token;
        private String secret;

        public JWTVerifier token(String token){
            this.token = token;
            return this;
        }

        public JWTVerifier secret(String secret){
            this.secret = secret;
            return this;
        }

        public Result<DecodedJWT> verify(){
            try {
                Algorithm algorithm = Algorithm.HMAC256(secret);
                com.auth0.jwt.JWTVerifier verifier = com.auth0.jwt.JWT.require(algorithm)
                        .build();
                DecodedJWT jwt = verifier.verify(token);
                return Result.success(jwt);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("校验失败");
            }
            return Result.fail("校验失败");
        }
    }
}
