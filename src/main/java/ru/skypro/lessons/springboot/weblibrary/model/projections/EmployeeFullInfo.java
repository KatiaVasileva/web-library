package ru.skypro.lessons.springboot.weblibrary.model.projections;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class EmployeeFullInfo {

    private String name;

    private int salary;

    private String positionName;

    public EmployeeFullInfo(String name, int salary, String positionName) {
        this.name = name;
        this.salary = salary;
        this.positionName = positionName;
    }
}
