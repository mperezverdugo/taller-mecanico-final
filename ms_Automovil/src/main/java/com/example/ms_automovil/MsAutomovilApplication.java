package com.example.ms_automovil;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MsAutomovilApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsAutomovilApplication.class, args);
    }



}
