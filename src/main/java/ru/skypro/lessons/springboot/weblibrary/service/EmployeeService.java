package ru.skypro.lessons.springboot.weblibrary.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.lessons.springboot.weblibrary.dto.CreateEmployee;
import ru.skypro.lessons.springboot.weblibrary.dto.EmployeeDTO;
import ru.skypro.lessons.springboot.weblibrary.model.projections.EmployeeFullInfo;

import java.io.IOException;
import java.util.List;

public interface EmployeeService {

    List<EmployeeDTO> findAllEmployees();

    String findTotalSalary();

    List<EmployeeDTO> findEmployeesWithMinSalary();

    List<EmployeeDTO> findEmployeesWithMaxSalary();

    List<EmployeeFullInfo> findEmployeesWithHighSalary();

    CreateEmployee addEmployee(CreateEmployee employee);

    CreateEmployee editEmployee(int id, CreateEmployee createUpdatedEmployee);

    EmployeeDTO getEmployeeById(int id);

    EmployeeDTO deleteEmployeeById(int id);

    List<EmployeeDTO> findBySalaryGreaterThan(int salary);

    List<EmployeeFullInfo> findEmployeeFullInfoWithMaxSalary();

    List<EmployeeDTO> getEmployeesByDepartment(String position);

    EmployeeFullInfo getEmployeeFullInfoById(int id);

    List<EmployeeDTO> getEmployeeWithPaging(Integer pageIndex, Integer unitPerPage);

    List<EmployeeDTO> uploadFile(MultipartFile file) throws IOException;

}
