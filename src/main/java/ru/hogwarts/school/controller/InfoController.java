package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/port")
public class InfoController {

    @Value("${server.port}")
    private int serverPort;

    @GetMapping
    @Profile("test")
    public String getPortTest() {
        return "Порт в режиме разработки: " + serverPort;
    }

    @GetMapping
    @Profile("prod")
    public String getPortProduction() {
        return "Порт в режиме разработки: " + serverPort;
    }

}
