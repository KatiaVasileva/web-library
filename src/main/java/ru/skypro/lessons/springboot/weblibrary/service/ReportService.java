package ru.skypro.lessons.springboot.weblibrary.service;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface ReportService {

    int createReport() throws IOException;

    ResponseEntity<Resource> downloadFileById(int id) throws FileNotFoundException;
}
