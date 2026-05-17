package com.example.ms_ordentrabajo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MsOrdenTrabajoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsOrdenTrabajoApplication.class, args);
    }

}
