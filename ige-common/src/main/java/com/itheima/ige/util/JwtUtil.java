package com.itheima.ige.util;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/*****
 * @Author:
 * @Description: JWT工具类
 ****/
public class JwtUtil {

    //秘钥
    public static final String SECRETADMIN="ADMIN5pil6aOO5YaN576O5Lmf5q+U5LiN5LiK5bCP6ZuF55qE56yR";//管理员
    public static final long expiration=1000*60*60*24*7;

    /***
     * 生成令牌-管理员
     * @return
     * @throws Exception
     */
    public static String generate(Map<String,Object> payload) throws Exception {
        return generate(payload,expiration,SECRETADMIN);
    }

    /***
     * 生成令牌
     * @param ttlMillis:有效期
     * @return
     * @throws Exception
     */
    public static String generate(Map<String,Object> payload, long ttlMillis,String secret) throws Exception {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        Key signingKey = new SecretKeySpec(secret.getBytes(), signatureAlgorithm.getJcaName());
        Map<String,Object> header=new HashMap<String,Object>();
        header.put("typ","JWT");
        header.put("alg","HS256");
        String id=UUID.randomUUID().toString();
        JwtBuilder builder = Jwts.builder().setId(id)
                .setIssuedAt(now)
                .setIssuer(id)
                .setSubject(id)
                .setHeader(header)
                .signWith(signatureAlgorithm, signingKey);

        //设置载体
        builder.addClaims(payload);
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }
        return builder.compact();
    }


    /***
     * 解密JWT令牌
     */
    public static Map<String, Object> parse(String token){
        //以Bearer开头处理
        if(token.startsWith("Bearer")){
            token=token.substring(6).trim();
        }

        //秘钥处理
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        Key signingKey = new SecretKeySpec(SECRETADMIN.getBytes(), signatureAlgorithm.getJcaName());

        Claims claims = Jwts.parser()
                .setSigningKey(signingKey)
                .parseClaimsJws(token)
                .getBody();
        return claims;
    }
}



