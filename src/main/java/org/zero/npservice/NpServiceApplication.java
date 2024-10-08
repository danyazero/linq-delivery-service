package org.zero.npservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class NpServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(NpServiceApplication.class, args);
    }

}