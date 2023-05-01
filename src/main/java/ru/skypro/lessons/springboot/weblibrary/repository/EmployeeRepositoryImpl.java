package ru.skypro.lessons.springboot.weblibrary.repository;

import org.springframework.stereotype.Repository;
import ru.skypro.lessons.springboot.weblibrary.pojo.Employee;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class EmployeeRepositoryImpl implements EmployeeRepository{

    private final List<Employee> employeeList = List.of(
            new Employee("Kate", 90_000),
            new Employee("John", 102_000),
            new Employee("Ben", 80_000),
            new Employee("Mary", 105_000),
            new Employee("David", 80_000)
    );

    @Override
    public List<Employee> getAllEmployees() {
        return employeeList;
    }

    @Override
    public String getSalarySum() {
        return "Сумма зарплат сотрудников: " + getSalaryStatics().getSum() + " руб.";
    }

    @Override
    public List<Employee> getEmployeesWithMinSalary() {
        return employeeList.stream()
                .filter(employee -> employee.getSalary() == getSalaryStatics().getMin())
                .collect(Collectors.toList());
    }

    @Override
    public List<Employee>  getEmployeesWithMaxSalary() {
        return employeeList.stream()
                .filter(employee -> employee.getSalary() == getSalaryStatics().getMax())
                .collect(Collectors.toList());
    }

    @Override
    public List<Employee> getEmployeesWithHighSalary() {
        return employeeList.stream()
                .filter(employee -> employee.getSalary() > getSalaryStatics().getAverage())
                .collect(Collectors.toList());
    }

    public IntSummaryStatistics getSalaryStatics() {
        return employeeList.stream()
                .mapToInt(Employee::getSalary)
                .summaryStatistics();
    }
}
