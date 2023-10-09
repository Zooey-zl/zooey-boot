package com.cn.zooey;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;

@SpringBootTest
class ZooeyBootApplicationTests {

    @Resource
    private PasswordEncoder passwordEncoder;
    @Test
    void contextLoads() {

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();


        String pwd = "123456.zz";

        String encode = passwordEncoder.encode(pwd);

        System.out.println(bCryptPasswordEncoder.encode(pwd));

        System.out.println(encode);
    }

}
