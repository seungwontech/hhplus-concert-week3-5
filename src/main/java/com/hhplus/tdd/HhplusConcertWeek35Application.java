package com.hhplus.tdd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableAsync
@SpringBootApplication
public class HhplusConcertWeek35Application {

    public static void main(String[] args) {
        SpringApplication.run(HhplusConcertWeek35Application.class, args);
    }

}
