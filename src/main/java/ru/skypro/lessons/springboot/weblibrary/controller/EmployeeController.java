package ru.skypro.lessons.springboot.weblibrary.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.skypro.lessons.springboot.weblibrary.pojo.Employee;
import ru.skypro.lessons.springboot.weblibrary.service.EmployeeService;

import java.util.List;

@RestController
@RequestMapping("/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/total-employees")
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/salary/sum")
    public String getSalarySum() {
        return employeeService.getSalarySum();
    }

    @GetMapping("/salary/min")
    public List<Employee> getEmployeesWithMinSalary() {
        return employeeService.getEmployeesWithMinSalary();
    }

    @GetMapping("/salary/max")
    public List<Employee> getEmployeesWithMaxSalary() {
        return employeeService.getEmployeesWithMaxSalary();
    }

    @GetMapping("/high-salary")
    public List<Employee> getEmployeesWithHighSalary() {
        return employeeService.getEmployeesWithHighSalary();
    }

    @PostMapping("/new-employee")
    public void createNewEmployee(@RequestBody Employee employee) {
        employeeService.createNewEmployee(employee.getName(), employee.getSalary());
    }

}
