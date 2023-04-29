package ru.skypro.lessons.springboot.weblibrary.service;

import org.springframework.stereotype.Service;

@Service
public class CounterServiceImpl implements CounterService{
    private int counter;

    @Override
    public String showCounter() {
        return "Количество запросов: " + ++counter;
    }
}
