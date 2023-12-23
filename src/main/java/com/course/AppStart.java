package com.course;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author YangHaixiong
 * @date 2023/12/23 10:06
 */

@SpringBootApplication
@EnableSwagger2
public class AppStart {
    public static void main(String[] args) {
        SpringApplication.run(AppStart.class, args);
        System.out.println("http:localhost:8888/swagger-ui.html");
    }
}
