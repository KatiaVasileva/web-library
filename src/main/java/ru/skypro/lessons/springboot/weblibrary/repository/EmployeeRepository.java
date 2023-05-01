package ru.skypro.lessons.springboot.weblibrary.repository;

import ru.skypro.lessons.springboot.weblibrary.pojo.Employee;

import java.util.List;

public interface EmployeeRepository {
    List<Employee> getAllEmployees();

    String getSalarySum();

    List<Employee> getEmployeesWithMinSalary();

    List<Employee> getEmployeesWithMaxSalary();

    List<Employee> getEmployeesWithHighSalary();
}
