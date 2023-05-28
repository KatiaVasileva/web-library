package ru.skypro.lessons.springboot.weblibrary.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.lessons.springboot.weblibrary.dto.EmployeeDTO;
import ru.skypro.lessons.springboot.weblibrary.model.Employee;
import ru.skypro.lessons.springboot.weblibrary.model.projections.EmployeeFullInfo;

import java.io.IOException;
import java.util.List;

public interface EmployeeService {

    List<EmployeeDTO> findAllEmployees();

    String findTotalSalary();

    List<EmployeeDTO> findEmployeesWithMinSalary();

    List<EmployeeDTO> findEmployeesWithMaxSalary();

    List<EmployeeFullInfo> findEmployeesWithHighSalary();

    void addEmployee(Employee employee);

    void editEmployee(int id, Employee updatedEmployee);

    EmployeeDTO getEmployeeById(int id);

    void deleteEmployeeById(int id);

    List<EmployeeDTO> findBySalaryGreaterThan(int salary);

    List<EmployeeFullInfo> findEmployeeFullInfoWithMaxSalary();

    List<EmployeeDTO> getEmployeesByDepartment(String name);

    EmployeeFullInfo getEmployeeFullInfoById(int id);

    List<EmployeeDTO> getEmployeeWithPaging(Integer pageIndex, Integer unitPerPage);

    void uploadFile(MultipartFile file) throws IOException;

}
