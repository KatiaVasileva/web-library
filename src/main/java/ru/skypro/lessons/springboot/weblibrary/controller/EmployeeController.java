package ru.skypro.lessons.springboot.weblibrary.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.skypro.lessons.springboot.weblibrary.dto.EmployeeDTO;
import ru.skypro.lessons.springboot.weblibrary.model.projections.EmployeeFullInfo;
import ru.skypro.lessons.springboot.weblibrary.service.EmployeeService;

import java.util.List;

@RestController
@RequestMapping("/employee")
@RequiredArgsConstructor
@Tag(name = "Сотрудники (USER)", description = "Получение данных по сотрудникам (права доступа - ADMIN, USER)")
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/total-employees")
    @Operation(summary = "Получение списка сотрудников", description = "Получить информацию о всех сотрудниках " +
            "в базе данных.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Список всех сотрудников", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = EmployeeDTO.class))})})
    public List<EmployeeDTO> findAllEmployees() {
        return employeeService.findAllEmployees();
    }

    @GetMapping("/salary/sum")
    @Operation(summary = "Получение суммы зарплат сотрудников", description = "Получить информацию об общей сумме всех " +
            "зарплат сотрудников.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Сумма зарплат")})
    public String findTotalSalary() {
        return employeeService.findTotalSalary();
    }

    @GetMapping("/salary/min")
    @Operation(summary = "Получение информации о сотрудниках с минимальной зарплатой",
            description = "Получить информацию о сотрудниках с минимальной зарплатой (ID, имя, зарплата).")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Сотрудники с минимальной зарплатой", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = EmployeeDTO.class))})})
    public List<EmployeeDTO> findEmployeesWithMinSalary() {
        return employeeService.findEmployeesWithMinSalary();
    }

    @GetMapping("/salary/max")
    @Operation(summary = "Получение информации о сотрудниках с максимальной зарплатой",
            description = "Получить информацию о сотрудниках с максимальной зарплатой (ID, имя, зарплата).")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Сотрудники с максимальной зарплатой", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = EmployeeDTO.class))})})
    public List<EmployeeDTO> findEmployeesWithMaxSalary() {
        return employeeService.findEmployeesWithMaxSalary();
    }

    @GetMapping("/high-salary")
    @Operation(summary = "Получение всех сотрудников, зарплата которых больше средней",
            description = "Получить полную информацию о сотрудниках (имя, зарплата, должность), зарплата которых больше средней.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Сотрудники с высокой зарплатой", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = EmployeeFullInfo.class))})})
    public List<EmployeeFullInfo> findEmployeesWithHighSalary() {
        return employeeService.findEmployeesWithHighSalary();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение информации о сотруднике по ID",
            description = "Получить информацию о сотруднике по указанному идентификационному номеру.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Сотрудник по указанному ID", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = EmployeeDTO.class))}),
            @ApiResponse(
                    responseCode = "404", description = "Сотрудник не найден")
    })
    public EmployeeDTO getEmployeeById(@PathVariable int id) {
        return employeeService.getEmployeeById(id);
    }

    @GetMapping("/salaryHigherThan")
    @Operation(summary = "Получение информации о сотрудниках с определенной зарплатой",
            description = "Получить информацию о сотрудниках, зарплата которых выше указанного значения.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Сотрудники с заданной зарплатой", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = EmployeeDTO.class))}),
            @ApiResponse(
                    responseCode = "400", description = "Некорректный запрос")
    })
    public List<EmployeeDTO> getEmployeesWithSalaryHigherThan(@RequestParam("salary") int salary) {
        return employeeService.findBySalaryGreaterThan(salary);
    }

    @GetMapping("/withHighestSalary")
    @Operation(summary = "Получение информации о сотрудниках с самой высокой зарплатой",
            description = "Получить полную информацию о сотрудниках (имя, зарплата, должность) с самой высокой зарплатой.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Сотрудники с самой высокой зарплатой", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = EmployeeFullInfo.class))})})
    public List<EmployeeFullInfo> findEmployeeFullInfoWithMaxSalary() {
        return employeeService.findEmployeeFullInfoWithMaxSalary();
    }

    @GetMapping("")
    @Operation(summary = "Получение информации о сотрудниках указанного отдела (если отдел не указан - о всех сотрудниках).")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Сотрудники указанного отдела", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = EmployeeDTO.class))}),
    })
    public List<EmployeeDTO> getEmployeesByDepartment(@RequestParam(value = "position", required = false) String position) {
        return employeeService.getEmployeesByDepartment(position);
    }

    @GetMapping("/{id}/fullInfo")
    @Operation(summary = "Получение полной информации о сотруднике по ID",
            description = "Получить полную информацию о сотруднике (имя, зарплата, должность) по указанному идентификационному номеру.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Сотрудник по указанному ID", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = EmployeeFullInfo.class))}),
            @ApiResponse(
                    responseCode = "404", description = "Сотрудник не найден")
    })
    public EmployeeFullInfo getEmployeeFullInfoById(@PathVariable int id) {
        return employeeService.getEmployeeFullInfoById(id);
    }

    @GetMapping("/page")
    @Operation(summary = "Получение информации о сотрудниках на основе номера страницы",
            description = "Получить все записи о сотруднике по номеру страницы. " +
                    "Если страница не указана, возвращает первую страницу.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Информация о сотрудниках на указанной странице", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = EmployeeDTO.class))}),
    })
    public List<EmployeeDTO> getEmployeeWithPaging(@RequestParam(value = "page", required = false) Integer pageIndex,
                                                   Integer unitPerPage) {
        return employeeService.getEmployeeWithPaging(pageIndex, unitPerPage);
    }
}
