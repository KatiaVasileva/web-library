package ru.skypro.lessons.springboot.weblibrary.service;

import ru.skypro.lessons.springboot.weblibrary.pojo.Employee;

import java.util.List;

public interface EmployeeService {

    List<Employee> getAllEmployees();

    String getSalarySum();

    List<Employee> getEmployeesWithMinSalary();

    List<Employee> getEmployeesWithMaxSalary();

    List<Employee> getEmployeesWithHighSalary();

    void addEmployeeList(List<Employee> newEmployeeList);

    void editEmployee(int id, Employee updatedEmployee);

    Employee getEmployeeById(int id);

    void deleteEmployeeById(int id);

    List<Employee> getEmployeesWithSalaryHigherThan(int salary);
}
