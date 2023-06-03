package ru.skypro.lessons.springboot.weblibrary.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.lessons.springboot.weblibrary.service.ReportService;

import java.io.FileNotFoundException;
import java.io.IOException;

@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
@Tag(name = "Отчет", description = "Создание и получение отчета по сотрудникам и отделам")
public class ReportController {

    private final ReportService reportService;

    @PostMapping("")
    @Operation(summary = "Формирование отчета со статистикой по отделам", description = "Формирует отчет JSON со статистикой  " +
            "по отделам, сохраняет файл в базе данных, возвращает ID файла, сохраненного в базе данных.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Отчет сформирован")
    })
    public int createReport() throws IOException {
        return reportService.createReport();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @Operation(summary = "Получение отчета из базы данных по ID", description = "Возвращает ранее созданный отчет " +
            "по идентификационному номеру.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Отчет получен", content = {
                    @Content(mediaType = "application/octet-stream")}),
            @ApiResponse(responseCode = "404", description = "Отчет не найден")})
    public ResponseEntity<Resource> downloadFileById(@PathVariable int id) throws FileNotFoundException {
        return reportService.downloadFileById(id);
    }
}
