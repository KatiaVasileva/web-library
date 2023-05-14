package ru.skypro.lessons.springboot.weblibrary.repository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import ru.skypro.lessons.springboot.weblibrary.exceptions.EmployeeNotFoundException;
import ru.skypro.lessons.springboot.weblibrary.pojo.Employee;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class EmployeeRepositoryImpl implements EmployeeRepository{

    private List<Employee> employeeList = new ArrayList<>(List.of(
            new Employee("Kate", 90_000),
            new Employee("John", 102_000),
            new Employee("Ben", 80_000),
            new Employee("Mary", 165_000)
    ));

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

    @Override
    public void addEmployeeList(List<Employee> newEmployeeList) {
        employeeList.addAll(newEmployeeList);
    }

    @Override
    public void editEmployee(int id, Employee updatedEmployee) {
        for (Employee employee : employeeList) {
            if (employee.getId() == id) {
                employee.setName(updatedEmployee.getName());
                employee.setSalary(updatedEmployee.getSalary());
            }
        }
        throw new EmployeeNotFoundException(HttpStatus.BAD_REQUEST, "Сотрудник не найден!");

    }

    @Override
    public Employee getEmployeeById(int id) {
        for (Employee employee : employeeList) {
            if (employee.getId() == id) {
                return employee;
            }
        }
        throw new EmployeeNotFoundException(HttpStatus.BAD_REQUEST, "Сотрудник не найден!");
    }

    @Override
    public void deleteEmployeeById(int id) {
        if (!employeeList.removeIf(employee -> employee.getId() == id)) {
            throw new EmployeeNotFoundException(HttpStatus.BAD_REQUEST, "Сотрудник не найден!");
        }
    }

    @Override
    public List<Employee> getEmployeesWithSalaryHigherThan(int salary) {
        return employeeList.stream()
                .filter(employee -> employee.getSalary() > salary)
                .collect(Collectors.toList());
    }

    public IntSummaryStatistics getSalaryStatics() {
        return employeeList.stream()
                .mapToInt(Employee::getSalary)
                .summaryStatistics();
    }
}
