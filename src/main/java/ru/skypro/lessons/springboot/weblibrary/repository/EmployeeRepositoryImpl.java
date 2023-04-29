package ru.skypro.lessons.springboot.weblibrary.repository;

import org.springframework.stereotype.Repository;
import ru.skypro.lessons.springboot.weblibrary.pojo.Employee;

import java.util.List;

@Repository
public class EmployeeRepositoryImpl implements EmployeeRepository{

    private final List<Employee> employeeList = List.of(
            new Employee("Kate", 90_000),
            new Employee("John", 102_000),
            new Employee("Ben", 80_000),
            new Employee("Mary", 165_000)
    );

    @Override
    public List<Employee> getAllEmployees() {
        return employeeList;
    }
}
