package ru.skypro.lessons.springboot.weblibrary.model.projections;

import org.springframework.beans.factory.annotation.Value;

public interface EmployeeInfo {

    @Value("#{target.name + ', ' + target.salary + ' rubles'}")
    String getFullInfo();

}
