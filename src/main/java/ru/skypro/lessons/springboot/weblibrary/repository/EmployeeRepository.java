package ru.skypro.lessons.springboot.weblibrary.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.skypro.lessons.springboot.weblibrary.model.Employee;
import ru.skypro.lessons.springboot.weblibrary.model.projections.EmployeeFullInfo;


import java.util.List;

public interface EmployeeRepository extends CrudRepository<Employee, Integer> {

    @Query(value = "SELECT SUM(salary) FROM employees", nativeQuery = true)
    String findTotalSalary();

    @Query(value = "SELECT * FROM employees WHERE salary = (SELECT MIN(salary) FROM employees);",
            nativeQuery = true)
    List<Employee> findEmployeesWithMinSalary();

    @Query(value = "SELECT * FROM employees WHERE salary = (SELECT MAX(salary) FROM employees);",
            nativeQuery = true)
    List<Employee> findEmployeesWithMaxSalary();

    @Query(value = "SELECT new ru.skypro.lessons.springboot.weblibrary.model.projections." +
            "EmployeeFullInfo(e.name, e.salary, p.name) " +
            "FROM Employee e join fetch Position p " +
            "WHERE e.position = p AND e.salary > (SELECT AVG(e.salary) from Employee e)")
    List<EmployeeFullInfo> findEmployeesWithHighSalary();

    @Query(value = "SELECT * FROM employees", nativeQuery = true)
    List<Employee> findAllEmployees();

    List<Employee> findBySalaryGreaterThan(int salary);

    @Query(value = "SELECT new ru.skypro.lessons.springboot.weblibrary.model.projections." +
            "EmployeeFullInfo(e.name, e.salary, p.name) " +
            "FROM Employee e join fetch Position p " +
            "WHERE e.position = p AND e.salary = (SELECT MAX(e.salary) from Employee e)")
    List<EmployeeFullInfo> findEmployeeFullInfoWithMaxSalary();

    @Query(value = "SELECT new ru.skypro.lessons.springboot.weblibrary.model." +
            "Employee(e.id, e.name, e.salary) FROM Employee e join fetch Position p " +
            "WHERE e.position = p AND p.name = ?1")
    List<Employee> getEmployeesByDepartment(String name);

    @Query(value = "SELECT new ru.skypro.lessons.springboot.weblibrary.model.projections." +
            "EmployeeFullInfo(e.name, e.salary, p.name) " +
            "FROM Employee e join fetch Position p " +
            "WHERE e.position = p AND e.id = ?1")
    EmployeeFullInfo getEmployeeFullInfoById(int id);

    @Query(value = "SELECT new ru.skypro.lessons.springboot.weblibrary.model." +
            "Employee(e.id, e.name, e.salary) " +
            "FROM Employee e join fetch Position p WHERE e.position = p")
    Page<Employee> findAll(Pageable employeeOfConcretePage);
}
