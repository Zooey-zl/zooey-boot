package com.cn.zooey.util;

import com.cn.zooey.constant.GlobalConstant;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 * @author Fengzl
 * @date 2023/10/7 13:10
 * @desc
 **/
public class JWTUtil {

    /**
     * 加密算法
     */
    private static final SecretKey key = Jwts.SIG.HS256.key().build();


    /**
     * 生成JWT
     * @param userName
     * @return
     */
    public static String generate(String userName) {

        // 过期时间
        Date expiryDate = new Date(System.currentTimeMillis() + GlobalConstant.JWT_EXPIRATION.toMillis());

        return Jwts.builder()
                // 将userName放进JWT
                .subject(userName)
                // 设置JWT签发时间
                .issuedAt(new Date())
                // 设置过期时间
                .expiration(expiryDate)
                // 设置加密算法
                .signWith(key).compact();
    }


    /**
     * 解析token
     * @param token
     * @return
     */
    public static Claims parse(String token) {

        if (StringUtils.isBlank(token)) {
            return null;
        }
        Claims claims = null;

        try {
            claims = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

        } catch (JwtException e) {
            System.out.println("解析失败:" + e.getMessage());
        }

        return claims;
    }


}
