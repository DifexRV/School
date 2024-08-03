package ru.hogwarts.school.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.IntStream;

@RestController
@RequestMapping("/port")
public class InfoController {

    private final Logger logger = LoggerFactory.getLogger(InfoController.class);

    @Value("${server.port}")
    private int serverPort;

    @GetMapping
    public String getPort() {
        return "Порт в режиме разработки: " + serverPort;
    }

    @GetMapping("/sum")
    public int getSum() {
        logger.info("Started working on calculating the amount");
        long startTime = System.currentTimeMillis();

        int sum = IntStream.rangeClosed(1, 1_000_000)
                .parallel()
                .sum();

        logger.info("Finished work on calculating the amount");
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        logger.info("Total execution time: " + duration + " ms");
        return sum;
    }

}
