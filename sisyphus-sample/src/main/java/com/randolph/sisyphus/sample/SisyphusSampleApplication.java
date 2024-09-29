package com.randolph.sisyphus.sample;

import jakarta.websocket.server.PathParam;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SisyphusSampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SisyphusSampleApplication.class, args);
    }

    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "world") String name){
        return String.format("hello %s!", name);
    }

}
