package com.h10.sideproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SideprojectApplication {

    public static void main(String[] args) {
        SpringApplication.run(SideprojectApplication.class, args);
    }

}
