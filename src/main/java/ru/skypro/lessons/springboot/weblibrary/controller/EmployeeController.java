package ru.skypro.lessons.springboot.weblibrary.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.skypro.lessons.springboot.weblibrary.dto.EmployeeDTO;
import ru.skypro.lessons.springboot.weblibrary.model.Employee;
import ru.skypro.lessons.springboot.weblibrary.model.projections.EmployeeFullInfo;
import ru.skypro.lessons.springboot.weblibrary.service.EmployeeService;

import java.util.List;

@RestController
@RequestMapping("/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    // 1. Получение списка сотрудников
    @GetMapping("/total-employees")
    public List<EmployeeDTO> findAllEmployees() {
        return employeeService.findAllEmployees();
    }

    // 2. Получение суммы зарплат сотрудников
    @GetMapping("/salary/sum")
    public String findTotalSalary() {
        return employeeService.findTotalSalary();
    }

    // 3. Получение полной информации о сотрудниках с минимальной зарплатой
    @GetMapping("/salary/min")
    public List<EmployeeDTO> findEmployeesWithMinSalary() {
        return employeeService.findEmployeesWithMinSalary();
    }

    // 4. Получение информации о сотрудниках с максимальной зарплатой
    @GetMapping("/salary/max")
    public List<EmployeeDTO> findEmployeesWithMaxSalary() {
        return employeeService.findEmployeesWithMaxSalary();
    }

    // 5. Получение всех сотрудников, зарплата которых больше средней
    @GetMapping("/high-salary")
    public List<EmployeeFullInfo> findEmployeesWithHighSalary() {
        return employeeService.findEmployeesWithHighSalary();
    }

    // 6. Создание нового сотрудника
    @PostMapping("/")
    public void addEmployee(@RequestBody Employee employee) {
        employeeService.addEmployee(employee);
    }

    // 7. Редактирование сотрудника по указанному идентификационному номеру
    @PutMapping("/{id}")
    public void editEmployee(@PathVariable int id, @RequestBody Employee updatedEmployee) {
        employeeService.editEmployee(id, updatedEmployee);
    }

    // 8. Получение информации о сотруднике по указанному идентификационному номеру
    @GetMapping("/{id}")
    public EmployeeDTO getEmployeeById(@PathVariable int id) {
        return employeeService.getEmployeeById(id);
    }

    // 9. Удаление сотрудника по указанному идентификационному номеру
    @DeleteMapping("/{id}")
    public void deleteEmployeeById(@PathVariable int id) {
        employeeService.deleteEmployeeById(id);
    }

    // 10. Получение всех сотрудников, зарплата которых выше переданного параметра salary
    @GetMapping("/salaryHigherThan")
    public List<EmployeeDTO> getEmployeesWithSalaryHigherThan(@RequestParam("salary") int salary) {
        return employeeService.findBySalaryGreaterThan(salary);
    }

    // 11. Получение полной информации о сотрудниках с самой высокой зарплатой
    @GetMapping("/withHighestSalary")
    public List<EmployeeFullInfo> findEmployeeFullInfoWithMaxSalary() {
        return employeeService.findEmployeeFullInfoWithMaxSalary();
    }

    // 12. Получение информации о сотрудниках указанного отдела (если отдел не указан - о всех сотрудниках)
    @GetMapping("")
    public List<EmployeeDTO> getEmployeesByDepartment(@RequestParam(value = "position", required = false) String name) {
        return employeeService.getEmployeesByDepartment(name);
    }

    // 13. Получение полной информации о сотруднике по указанному идентификационному номеру
    @GetMapping("/{id}/fullInfo")
    public EmployeeFullInfo getEmployeeFullInfoById(@PathVariable int id) {
        return employeeService.getEmployeeFullInfoById(id);
    }

    // 14. Получение информации о сотрудниках на основе номера страницы
    // (если страница не указана - то возвращается первая страница)
    @GetMapping("/page")
    public List<EmployeeDTO> getEmployeeWithPaging(@RequestParam(value = "page", required = false) Integer pageIndex,
                                                Integer unitPerPage) {
        return employeeService.getEmployeeWithPaging(pageIndex, unitPerPage);
    }
}
