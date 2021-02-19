package com.myclass.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@ComponentScan(value = "com.myclass")
@EnableJpaRepositories("com.myclass.repository")
@EntityScan("com.myclass.entity")
public class DemoApplication {
    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder(){return new BCryptPasswordEncoder();}
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
