package ru.skypro.lessons.springboot.weblibrary.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class InfoController {

    @Value("${app.env}")
    private String environment;

    @GetMapping("/appInfo")
    public String getAppInfo() {
        return environment;
    }
}
