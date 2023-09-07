package com.cn.zooey;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@MapperScan(basePackages = {"com.cn.zooey.mapper"})
@ServletComponentScan(basePackages = {"com.cn.zooey"})
public class ZooeyBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZooeyBootApplication.class, args);
    }

}
