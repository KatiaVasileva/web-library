package ru.skypro.lessons.springboot.weblibrary.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.lessons.springboot.weblibrary.dto.CreateEmployee;
import ru.skypro.lessons.springboot.weblibrary.dto.EmployeeDTO;
import ru.skypro.lessons.springboot.weblibrary.exceptions.EmployeeNotFoundException;
import ru.skypro.lessons.springboot.weblibrary.model.Employee;
import ru.skypro.lessons.springboot.weblibrary.model.Position;
import ru.skypro.lessons.springboot.weblibrary.model.projections.EmployeeFullInfo;
import ru.skypro.lessons.springboot.weblibrary.repository.EmployeeRepository;


import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
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
    public void addEmployee(CreateEmployee employee) {
        employeeRepository.save(employee.toEmployee());
    }

    @Override
    public void editEmployee(int id, CreateEmployee createUpdatedEmployee) {
        Employee updatedEmployee = createUpdatedEmployee.toEmployee();
        Employee employee = employeeRepository.findById(id).orElseThrow(EmployeeNotFoundException::new);
        employee.setName(updatedEmployee.getName());
        employee.setSalary(updatedEmployee.getSalary());
        employee.setPosition(new Position(updatedEmployee.getPosition().getId(), updatedEmployee.getPosition().getName()));
        employeeRepository.save(employee);
    }

    @Override
    public EmployeeDTO getEmployeeById(int id) {
        return employeeRepository.findById(id).stream()
                .map(EmployeeDTO::fromEmployee)
                .findAny().orElseThrow(EmployeeNotFoundException::new);
    }

    @Override
    public void deleteEmployeeById(int id) {
        employeeRepository.findById(id).stream()
                .map(EmployeeDTO::fromEmployee)
                .findAny().orElseThrow(EmployeeNotFoundException::new);
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
    public List<EmployeeDTO> getEmployeesByDepartment(String position) {
        if (position.isEmpty()) {
            return employeeRepository.findAllEmployees().stream()
                    .map(EmployeeDTO::fromEmployee)
                    .collect(Collectors.toList());
        } else {
            return employeeRepository.getEmployeesByDepartment(position).stream()
                    .map(EmployeeDTO::fromEmployee)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public EmployeeFullInfo getEmployeeFullInfoById(int id) {
        if (employeeRepository.getEmployeeFullInfoById(id) != null) {
            return employeeRepository.getEmployeeFullInfoById(id);
        }
        throw new EmployeeNotFoundException();
    }

    @Override
    public List<EmployeeDTO> getEmployeeWithPaging(Integer pageIndex, Integer unitPerPage) {
        Pageable employeeOfConcretePage = PageRequest.of(Objects.requireNonNullElse(pageIndex, 0), 10);
        return employeeRepository.findAll(employeeOfConcretePage).stream()
                .map(EmployeeDTO::fromEmployee).collect(Collectors.toList());
    }

    @Override
    public void uploadFile(MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        int streamSize = inputStream.available();
        byte[] bytes = new byte[streamSize];
        inputStream.read(bytes);
        String json = new String(bytes, StandardCharsets.UTF_8);
        ObjectMapper objectMapper = new ObjectMapper();
        List<Employee> employees = objectMapper.readValue(json, new TypeReference<>(){});
        employeeRepository.saveAll(employees);
    }

}
