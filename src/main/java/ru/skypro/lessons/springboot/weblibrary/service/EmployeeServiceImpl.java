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
    public List<Employee> getEmployeesWithMinSalary() {
        return employeeRepository.getEmployeesWithMinSalary();
    }

    @Override
    public List<Employee>  getEmployeesWithMaxSalary() {
        return employeeRepository.getEmployeesWithMaxSalary();
    }

    @Override
    public List<Employee> getEmployeesWithHighSalary() {
        return employeeRepository.getEmployeesWithHighSalary();
    }

    @Override
    public void addEmployeeList(List<Employee> newEmployeeList) {
        employeeRepository.addEmployeeList(newEmployeeList);
    }

    @Override
    public void editEmployee(int id, Employee updatedEmployee) {
        employeeRepository.editEmployee(id, updatedEmployee);
    }

    @Override
    public Employee getEmployeeById(int id) {
        return employeeRepository.getEmployeeById(id);
    }

    @Override
    public void deleteEmployeeById(int id) {
        employeeRepository.deleteEmployeeById(id);
    }

    @Override
    public List<Employee> getEmployeesWithSalaryHigherThan(int salary) {
        return employeeRepository.getEmployeesWithSalaryHigherThan(salary);
    }
}
