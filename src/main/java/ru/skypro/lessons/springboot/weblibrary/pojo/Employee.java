package ru.skypro.lessons.springboot.weblibrary.pojo;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    private String name;
    private int salary;

    @Override
    public String toString() {
        return "Имя сотрудника: " + name + ", зарплата " + salary + " руб.";
    }
}
