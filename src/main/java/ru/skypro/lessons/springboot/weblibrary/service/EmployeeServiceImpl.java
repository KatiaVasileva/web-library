package ru.skypro.lessons.springboot.weblibrary.service;

import org.springframework.stereotype.Service;
import ru.skypro.lessons.springboot.weblibrary.pojo.Employee;
import ru.skypro.lessons.springboot.weblibrary.repository.EmployeeRepository;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.getAllEmployees();
    }

    @Override
    public String getSalarySum() {
        return employeeRepository.getSalarySum();
    }

    @Override
    public Employee getEmployeeWithMinSalary() {
        return employeeRepository.getEmployeeWithMinSalary();
    }

    @Override
    public Employee getEmployeeWithMaxSalary() {
        return employeeRepository.getEmployeeWithMaxSalary();
    }

    @Override
    public List<Employee> getEmployeesWithHighSalary() {
        return employeeRepository.getEmployeesWithHighSalary();
    }
}
