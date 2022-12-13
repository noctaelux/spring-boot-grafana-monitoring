package com.example.springbootmicrometermonitoring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SpringBootMicrometerMonitoringApplication {

    @GetMapping("/message")
    public String getMessage(){
        return "Hola Mundo!";
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBootMicrometerMonitoringApplication.class, args);
    }

}
