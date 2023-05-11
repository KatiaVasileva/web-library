package ru.skypro.lessons.springboot.weblibrary.service;

import ru.skypro.lessons.springboot.weblibrary.pojo.Employee;

import java.util.List;

public interface EmployeeService {

    List<Employee> getAllEmployees();

    String getSalarySum();

    List<Employee> getEmployeesWithMinSalary();

    List<Employee> getEmployeesWithMaxSalary();

    List<Employee> getEmployeesWithHighSalary();

    void createNewEmployee(String name, int salary);
    //void createNewEmployee(Employee employee);
}
