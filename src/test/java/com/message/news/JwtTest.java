package com.message.news;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Date;

@SpringBootTest
public class JwtTest {

    // 就运行这一个方法！
    @Test
    public void testGenToken() {
        // 第一句强制打印
        System.out.println("========= 代码开始执行 =========");

        try {
            // 生成Token（极简版）
            String token = JWT.create()
                    .withClaim("id", 1)
                    .withClaim("username", "admin")
                    .withExpiresAt(new Date(System.currentTimeMillis() + 3600000))
                    .sign(Algorithm.HMAC256("wendao"));

            // 打印Token
            System.out.println("生成的Token：" + token);

        } catch (Exception e) {
            // 强制打印错误
            System.out.println("报错了：");
            e.printStackTrace();
        }
    }

    @Test
    public void testParseToken() {
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MSwidXNlcm5hbWUiOiJhZG1pbiIsImV4cCI6MTc3NjQ4MTQ3MX0.w4BTKWXHP10MGxWr_-ornGO1E1xtVRr63xL3yA0eviU";
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("wendao"))
                .build(); // 验证
        DecodedJWT decodedJWT = jwtVerifier.verify(token); // 解析（验证失败会抛异常）
        System.out.println("解析的ID：" + decodedJWT.getClaim("id").asInt());
        System.out.println("解析的用户名：" + decodedJWT.getClaim("username").asString());
    }
}