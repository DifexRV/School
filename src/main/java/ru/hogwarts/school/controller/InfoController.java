package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/port")
public class InfoController {

    @Value("${server.port}")
    private int serverPort;

    @GetMapping
    public String getPortTest() {
        return "Порт в режиме разработки: " + serverPort;
    }

}
