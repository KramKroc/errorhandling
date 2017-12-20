package com.example.kramkroc.errorhandling;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import com.example.kramkroc.errorhandling.config.RestConfig;

@SpringBootApplication
@Import({ RestConfig.class })
public class ErrorhandlingApplication {

    public static void main(final String[] args) {
        SpringApplication.run(ErrorhandlingApplication.class, args);
    }
}
