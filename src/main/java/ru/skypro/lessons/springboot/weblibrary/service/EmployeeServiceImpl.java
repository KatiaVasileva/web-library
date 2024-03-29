package ru.skypro.lessons.springboot.weblibrary.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@Service
public class EmployeeServiceImpl implements EmployeeService{

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<EmployeeDTO> findAllEmployees() {
        List<EmployeeDTO> employeeDTOList = employeeRepository.findAllEmployees().stream()
                .map(EmployeeDTO::fromEmployee)
                .toList();
        LOGGER.info("All employees were found: {}", employeeDTOList);
        return employeeDTOList;
    }

    @Override
    public String findTotalSalary() {
        String totalSalary = employeeRepository.findTotalSalary();
        LOGGER.info("The total salary sum equal to {} rubles was received", totalSalary);
        return "Total salary sum equal to " +  totalSalary + " rubles was found.";
    }

    @Override
    public List<EmployeeDTO> findEmployeesWithMinSalary() {
        List<EmployeeDTO> employeeDTOList = employeeRepository.findEmployeesWithMinSalary().stream()
                .map(EmployeeDTO::fromEmployee)
                .toList();
        LOGGER.info("Employees with minimum salary were found: {}.", employeeDTOList);
        return employeeDTOList;
    }

    @Override
    public List<EmployeeDTO> findEmployeesWithMaxSalary() {
        List<EmployeeDTO> employeeDTOList = employeeRepository.findEmployeesWithMaxSalary().stream()
                .map(EmployeeDTO::fromEmployee)
                .toList();
        LOGGER.info("Employees with maximum salary were found: {}.", employeeDTOList);
        return employeeDTOList;
    }

    @Override
    public List<EmployeeFullInfo> findEmployeesWithHighSalary() {
        List<EmployeeFullInfo> employeeFullInfoList = employeeRepository.findEmployeesWithHighSalary();
        LOGGER.info("Employees with high salaries were found: {}", employeeFullInfoList);
        return employeeFullInfoList;
    }

    @Override
    public CreateEmployee addEmployee(CreateEmployee employee) {
        employeeRepository.save(employee.toEmployee());
        LOGGER.info("Employee was added: {}", employee);
        LOGGER.debug("Database was updated");
        return employee;
    }

    @Override
    public CreateEmployee editEmployee(int id, CreateEmployee createUpdatedEmployee) {
        try {
            Employee updatedEmployee = createUpdatedEmployee.toEmployee();
            Employee employee = employeeRepository.findById(id).orElseThrow(EmployeeNotFoundException::new);
            employee.setName(updatedEmployee.getName());
            employee.setSalary(updatedEmployee.getSalary());
            employee.setPosition(new Position(updatedEmployee.getPosition().getId(), updatedEmployee.getPosition().getName()));
            employeeRepository.save(employee);
            LOGGER.info("Employee was edited: {}", createUpdatedEmployee);
            LOGGER.debug("Database was updated");
            return CreateEmployee.fromEmployee(employee);
        } catch (EmployeeNotFoundException e) {
            LOGGER.error("There is no employee with id = " + id, e);
            throw new EmployeeNotFoundException();
        }
    }

    @Override
    public EmployeeDTO getEmployeeById(int id) {
        try {
             EmployeeDTO employeeDTO = employeeRepository.findById(id).stream()
                    .map(EmployeeDTO::fromEmployee)
                    .findAny().orElseThrow(EmployeeNotFoundException::new);
            LOGGER.info("Employee with id = {} was found: {}", id, employeeDTO);
            return employeeDTO;
        } catch (EmployeeNotFoundException e) {
            LOGGER.error("There is no employee with id = {}", id, e);
            throw new EmployeeNotFoundException();
        }
    }

    @Override
    public EmployeeDTO deleteEmployeeById(int id) {
        try {
            EmployeeDTO employeeDTO = employeeRepository.findById(id).stream()
                    .map(EmployeeDTO::fromEmployee)
                    .findAny().orElseThrow(EmployeeNotFoundException::new);
            employeeRepository.deleteById(id);
            LOGGER.info("Employee with id = {} was deleted: {}", id, employeeDTO);
            LOGGER.debug("Database was updated");
            return employeeDTO;
        } catch (EmployeeNotFoundException e) {
            LOGGER.error("There is no employee with id = {}", id, e);
            throw new EmployeeNotFoundException();
        }
    }

    @Override
    public List<EmployeeDTO> findBySalaryGreaterThan(int salary) {
        List<EmployeeDTO> employeeDTOList = employeeRepository.findBySalaryGreaterThan(salary).stream()
                .map(EmployeeDTO::fromEmployee)
                .toList();
        LOGGER.info("Employees with the salary greater than {} rubles were found: {}", salary, employeeDTOList);
        return employeeDTOList;
    }

    @Override
    public List<EmployeeFullInfo> findEmployeeFullInfoWithMaxSalary() {
        List<EmployeeFullInfo> employeeFullInfoList = employeeRepository.findEmployeeFullInfoWithMaxSalary();
        LOGGER.info("Employees (full info) with the maximum salary were found: {}", employeeFullInfoList);
        return employeeFullInfoList;
    }

    @Override
    public List<EmployeeDTO> getEmployeesByDepartment(String position) {
        List<EmployeeDTO> employeeDTOList;
        if (position.isEmpty() || position.isBlank()) {
            employeeDTOList = employeeRepository.findAllEmployees().stream()
                    .map(EmployeeDTO::fromEmployee)
                    .toList();
            LOGGER.info("All employees were found: {}", employeeDTOList);
        } else {
            employeeDTOList = employeeRepository.getEmployeesByDepartment(position).stream()
                    .map(EmployeeDTO::fromEmployee)
                    .toList();
            LOGGER.info("Employees from department {} were found: {}", position, employeeDTOList);
        }
        return employeeDTOList;
    }

    @Override
    public EmployeeFullInfo getEmployeeFullInfoById(int id) {
        try {
            EmployeeFullInfo employeeFullInfo = employeeRepository.getEmployeeFullInfoById(id);
            if (employeeFullInfo != null) {
                LOGGER.info("Full information about employee with id = {} was found: {}", id, employeeFullInfo);
                return employeeFullInfo;
            } else {
                throw new EmployeeNotFoundException();
            }
        } catch (EmployeeNotFoundException e) {
            LOGGER.error("There is no employee with id = {}", id, e);
            throw new EmployeeNotFoundException();
        }
    }

    @Override
    public List<EmployeeDTO> getEmployeeWithPaging(Integer pageIndex, Integer unitPerPage) {
        Pageable employeeOfConcretePage = PageRequest.of(Objects.requireNonNullElse(pageIndex, 0), unitPerPage);
        List<EmployeeDTO> employeeDTOList = employeeRepository.findAll(employeeOfConcretePage).stream()
                .map(EmployeeDTO::fromEmployee).toList();
        LOGGER.info("Page {} with employees was found: {}", pageIndex, employeeDTOList);
        return employeeDTOList;
    }

    @Override
    public List<EmployeeDTO> uploadFile(MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        int streamSize = inputStream.available();
        byte[] bytes = new byte[streamSize];
        inputStream.read(bytes);
        String json = new String(bytes, StandardCharsets.UTF_8);
        ObjectMapper objectMapper = new ObjectMapper();
        List<Employee> employees = objectMapper.readValue(json, new TypeReference<>(){});
        employeeRepository.saveAll(employees);
        LOGGER.info("File with employees was uploaded: {}", employees);
        LOGGER.debug("Database was updated with file");
        return employees.stream().map(EmployeeDTO::fromEmployee).toList();
    }
}
