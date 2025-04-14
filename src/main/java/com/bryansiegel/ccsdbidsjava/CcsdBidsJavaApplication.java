package com.bryansiegel.ccsdbidsjava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class CcsdBidsJavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(CcsdBidsJavaApplication.class, args);
    }

}
