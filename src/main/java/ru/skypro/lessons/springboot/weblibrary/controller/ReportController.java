package ru.skypro.lessons.springboot.weblibrary.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.lessons.springboot.weblibrary.service.ReportService;

import java.io.IOException;

@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    // 1. Формирование отчета json со статистикой по отделам, сохранение файла в базе данных
    // с возвращением идентификатора файла, сохраненного в базе данных
    @PostMapping("")
    public int createReport() throws IOException {
        return reportService.createReport();
    }

    // 2. Получение ранее созданного файла по идентификационному номеру
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Resource> downloadFileById(@PathVariable int id) throws IOException {
        return reportService.downloadFileById(id);
    }
}
