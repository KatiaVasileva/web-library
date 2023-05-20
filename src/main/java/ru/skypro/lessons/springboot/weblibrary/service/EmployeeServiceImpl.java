package ru.skypro.lessons.springboot.weblibrary.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.skypro.lessons.springboot.weblibrary.dto.EmployeeDTO;
import ru.skypro.lessons.springboot.weblibrary.exceptions.EmployeeNotFoundException;
import ru.skypro.lessons.springboot.weblibrary.model.Employee;
import ru.skypro.lessons.springboot.weblibrary.model.projections.EmployeeFullInfo;
import ru.skypro.lessons.springboot.weblibrary.repository.EmployeeRepository;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<EmployeeDTO> findAllEmployees() {
        return employeeRepository.findAllEmployees().stream()
                .map(EmployeeDTO::fromEmployee)
                .collect(Collectors.toList());
    }

    @Override
    public String findTotalSalary() {
        return "Total salary sum is " + employeeRepository.findTotalSalary() + " rubles.";
    }

    @Override
    public List<EmployeeDTO> findEmployeesWithMinSalary() {
        return employeeRepository.findEmployeesWithMinSalary().stream()
                .map(EmployeeDTO::fromEmployee)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDTO> findEmployeesWithMaxSalary() {
        return employeeRepository.findEmployeesWithMaxSalary().stream()
                .map(EmployeeDTO::fromEmployee)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeFullInfo> findEmployeesWithHighSalary() {
        return employeeRepository.findEmployeesWithHighSalary();
    }

    @Override
    public void addEmployee(Employee employee) {
        employeeRepository.save(employee);
    }

    @Override
    public void editEmployee(int id, Employee updatedEmployee) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() ->
                new EmployeeNotFoundException((HttpStatus.BAD_REQUEST), "Employee is not found!"));
        employee.setName(updatedEmployee.getName());
        employee.setSalary(updatedEmployee.getSalary());
        employeeRepository.save(employee);
    }

    @Override
    public EmployeeDTO getEmployeeById(int id) {
        return employeeRepository.findById(id).stream()
                .map(EmployeeDTO::fromEmployee)
                .findAny().orElseThrow(() ->
                        new EmployeeNotFoundException((HttpStatus.BAD_REQUEST), "Employee is not found!"));
    }

    @Override
    public void deleteEmployeeById(int id) {
        employeeRepository.findById(id).stream()
                .map(EmployeeDTO::fromEmployee)
                .findAny().orElseThrow(() ->
                        new EmployeeNotFoundException((HttpStatus.BAD_REQUEST), "Employee is not found!"));
        employeeRepository.deleteById(id);
    }

    @Override
    public List<EmployeeDTO> findBySalaryGreaterThan(int salary) {
        return employeeRepository.findBySalaryGreaterThan(salary).stream()
                .map(EmployeeDTO::fromEmployee)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeFullInfo> findEmployeeFullInfoWithMaxSalary() {
        return employeeRepository.findEmployeeFullInfoWithMaxSalary();
    }

    @Override
    public List<EmployeeDTO> getEmployeesByDepartment(String name) {
        if (name.isEmpty()) {
            return employeeRepository.findAllEmployees().stream()
                    .map(EmployeeDTO::fromEmployee)
                    .collect(Collectors.toList());
        } else {
            return employeeRepository.getEmployeesByDepartment(name).stream()
                    .map(EmployeeDTO::fromEmployee)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public EmployeeFullInfo getEmployeeFullInfoById(int id) {
        if (employeeRepository.getEmployeeFullInfoById(id) != null) {
            return employeeRepository.getEmployeeFullInfoById(id);
        }
        throw new EmployeeNotFoundException(HttpStatus.BAD_REQUEST, "Employee is not found!");
    }

    @Override
    public List<Employee> getEmployeeWithPaging(Integer pageIndex, Integer unitPerPage) {
        unitPerPage = 10;
        if (pageIndex == null) {
            Pageable employeeOfConcretePage = PageRequest.of(0, unitPerPage);
            Page<Employee> page = employeeRepository.findAll(employeeOfConcretePage);
            return page.stream().toList();
        } else {
            Pageable employeeOfConcretePage = PageRequest.of(pageIndex, unitPerPage);
            Page<Employee> page = employeeRepository.findAll(employeeOfConcretePage);
            return page.stream().toList();
        }
    }
}
