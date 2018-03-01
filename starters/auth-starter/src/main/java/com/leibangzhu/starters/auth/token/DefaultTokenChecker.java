package com.leibangzhu.starters.auth.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.leibangzhu.starters.auth.Principal;
import com.leibangzhu.starters.common.Result;
import com.leibangzhu.starters.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;

public class DefaultTokenChecker implements ITokenChecker {

    @Autowired
    private ITokenSecretProvider secretProvider;

    @Override
    public Result<Principal> check(HttpServletRequest request) {
        String token = request.getHeader("token");
        if (StringUtil.isBlank(token)){
            return Result.fail(501,"没有token");
        }
        System.out.println("token:" + token);
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretProvider.get());
            JWTVerifier verifier = JWT.require(algorithm)
                    .build();
            DecodedJWT jwt = verifier.verify(token);

            Principal principal = Principal
                    .newBuilder()
                    .userId(jwt.getClaim(Principal.USER_ID).asString())
                    .roles(jwt.getClaim(Principal.ROLES).asString().split(","))
                    .permissions(jwt.getClaim(Principal.PERMISSIONS).asString().split(","))
                    .properties(getClaims(jwt))
                    .build();

            return Result.success(principal);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail(502,"token校验失败");
        }
    }

    private Map<String, String> getClaims(final DecodedJWT jwt) {
        Map<String, String> claims = new LinkedHashMap<>();
        Map<String, Claim> map = jwt.getClaims();
        for (Map.Entry<String, Claim> entry : map.entrySet()) {
            // 删除默认的exp属性，和userId。userId通过getUserId()来获取
            if (entry.getKey().equalsIgnoreCase("exp") || entry.getKey().equalsIgnoreCase(Principal.USER_ID)){
                continue;
            }
            claims.put(entry.getKey(), entry.getValue().asString());
        }
        return claims;
    }
}
