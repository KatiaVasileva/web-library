package ru.skypro.lessons.springboot.weblibrary.repository;

import org.springframework.stereotype.Repository;
import ru.skypro.lessons.springboot.weblibrary.pojo.Employee;

import java.util.*;

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

    @Override
    public String getSalarySum() {
        long totalSalary = getStatics().getSum();
        return "Сумма зарплат сотрудников: " + totalSalary + " руб.";
    }

    @Override
    public Employee getEmployeeWithMinSalary() {
        Employee employeeWithMinSalary = null;
        long minSalary = getStatics().getMin();
        for (Employee employee : employeeList) {
            if (employee.getSalary() == minSalary) {
                employeeWithMinSalary = employee;
            }
        }
        return employeeWithMinSalary;
    }

    @Override
    public Employee getEmployeeWithMaxSalary() {
        Employee employeeWithMaxSalary = null;
        long maxSalary = getStatics().getMax();
        for (Employee employee : employeeList) {
            if (employee.getSalary() == maxSalary) {
                employeeWithMaxSalary = employee;
            }
        }
        return employeeWithMaxSalary;
    }

    @Override
    public List<Employee> getEmployeesWithHighSalary() {
        List<Employee> employeesWithHighSalary = new ArrayList<>();
        double averageSalary = getStatics().getAverage();
        for (Employee employee : employeeList) {
            if (employee.getSalary() > averageSalary) {
                employeesWithHighSalary.add(employee);
            }
        }
        return employeesWithHighSalary;
    }

    public IntSummaryStatistics getStatics() {
        return employeeList.stream()
                .mapToInt(Employee::getSalary)
                .summaryStatistics();
    }
}
