package org.evgndev;

import org.evgndev.config.JpaConfig;
import org.evgndev.config.RestContextConfig;
import org.evgndev.config.SecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(
                new Class<?>[]{
                        Application.class,
                        JpaConfig.class,
                        SecurityConfig.class,
                        RestContextConfig.class
                }, args);
    }
}
