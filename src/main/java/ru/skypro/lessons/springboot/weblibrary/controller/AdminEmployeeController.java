package ru.skypro.lessons.springboot.weblibrary.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.lessons.springboot.weblibrary.dto.CreateEmployee;
import ru.skypro.lessons.springboot.weblibrary.service.EmployeeService;

import java.io.IOException;

@RestController
@RequestMapping("admin/employee")
@Tag(name = "Сотрудники (ADMIN)", description = "Создание, модификация и удаление данных по сотрудникам (права доступа - ADMIN)")
public class AdminEmployeeController {

    private final EmployeeService employeeService;

    public AdminEmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/")
    @Operation(summary = "Создание нового сотрудника", description = "Создает нового сотрудника. " +
            "При создании необходимо указать имя сотрудника, зарплату и идентификационный номер должности.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Сотрудник создан", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CreateEmployee.class))}),
            @ApiResponse(
                    responseCode = "400", description = "Некорректный запрос"),
            @ApiResponse(
                    responseCode = "403", description = "Доступ запрещен")
            })

    public void addEmployee(@Valid @RequestBody CreateEmployee employee) {
        employeeService.addEmployee(employee);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Редактирование сотрудника", description = "Редактирует сотрудника по указанному ID. " +
            "При редактировании необходимо указать новые параметры: имя сотрудника, зарплату и идентификационный номер должности.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Сотрудник изменен", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CreateEmployee.class))}),
            @ApiResponse(
                    responseCode = "400", description = "Некорректный запрос"),
            @ApiResponse(
                    responseCode = "404", description = "Сотрудник не найден"),
            @ApiResponse(
                    responseCode = "403", description = "Доступ запрещен")})
    public void editEmployee(@PathVariable int id, @Valid @RequestBody CreateEmployee createUpdatedEmployee) {
        employeeService.editEmployee(id, createUpdatedEmployee);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление сотрудника", description = "Удаляет сотрудника по указанному ID. ")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Сотрудник удален"),
            @ApiResponse(
                    responseCode = "404", description = "Сотрудник не найден"),
            @ApiResponse(
                    responseCode = "403", description = "Доступ запрещен")})
    public void deleteEmployeeById(@PathVariable int id) {
        employeeService.deleteEmployeeById(id);
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Загрузка списка сотрудников", description = "Загружает файл JSON cо списком сотрудников. " +
            "Сохраняет всех сотрудников из файла в базе данных.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Список сотрудников сохранен в базе данных", content = {
                    @Content(mediaType = "multipart/form-data")}),
            @ApiResponse(
                    responseCode = "400", description = "Некорректный запрос"),
            @ApiResponse(
                    responseCode = "403", description = "Доступ запрещен")})
    public void uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        employeeService.uploadFile(file);
    }
}
