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

    @PostMapping("/")
    public void addEmployeeList(@RequestBody List<Employee> newEmployeeList) {
        employeeService.addEmployeeList(newEmployeeList);
    }

    @PutMapping("/{id}")
    public void editEmployee(@PathVariable int id, @RequestBody Employee updatedEmployee) {
        employeeService.editEmployee(id, updatedEmployee);
    }

    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable int id) {
        return employeeService.getEmployeeById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteEmployeeByID(@PathVariable int id) {
        employeeService.deleteEmployeeById(id);
    }

    @GetMapping("/salaryHigherThan")
    public List<Employee> getEmployeesWithSalaryHigherThan(@RequestParam("salary") int salary) {
        return employeeService.getEmployeesWithSalaryHigherThan(salary);
    }
}
