package ru.skypro.lessons.springboot.weblibrary.pojo;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    private long id;
    private String name;
    private int salary;
    private static long idGenerator;

    public Employee(String name, int salary) {
        this.name = name;
        this.salary = salary;
        id = ++idGenerator;
    }

    @Override
    public String toString() {
        return "Имя сотрудника: " + name + ", зарплата " + salary + " руб.";
    }
}
