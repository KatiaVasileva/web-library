package ru.skypro.lessons.springboot.weblibrary.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EmployeeNotFoundException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "Employee is not found!";

    public EmployeeNotFoundException() {
        super(DEFAULT_MESSAGE);
    }
}
