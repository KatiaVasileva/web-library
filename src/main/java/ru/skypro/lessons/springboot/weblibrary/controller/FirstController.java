package ru.skypro.lessons.springboot.weblibrary.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.skypro.lessons.springboot.weblibrary.service.CounterService;

@RestController
@RequestMapping("/library")
public class FirstController {
    private final CounterService counterService;

    public FirstController(CounterService counterService) {
        this.counterService = counterService;
    }

    @GetMapping
    public String showHelloWorld() {
        return "Hello World"; // localhost:8080/library
    }

    @GetMapping("/counter")
    public String showCounter() {
        return counterService.showCounter(); // localhost:8080/library/counter
    }

}
