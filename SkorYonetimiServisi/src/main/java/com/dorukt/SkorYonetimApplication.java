package com.dorukt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SkorYonetimApplication {
    public static void main(String[] args) {
        SpringApplication.run(SkorYonetimApplication.class);
    }
}