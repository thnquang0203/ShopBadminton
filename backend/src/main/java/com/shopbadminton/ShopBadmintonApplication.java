package com.shopbadminton;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ShopBadmintonApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShopBadmintonApplication.class, args);
    }
}