package ru.skypro.lessons.springboot.weblibrary.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.skypro.lessons.springboot.weblibrary.model.projections.ReportEntry;
import ru.skypro.lessons.springboot.weblibrary.model.ReportFile;
import ru.skypro.lessons.springboot.weblibrary.repository.ReportRepository;

import java.io.*;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService{

    private final ReportRepository reportRepository;

    public ReportServiceImpl(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @Override
    public int createReport() throws IOException {
        List<ReportEntry> reportEntries = reportRepository.getReport();
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(reportEntries);
        ReportFile reportFile = new ReportFile(json);
        reportRepository.save(reportFile);
        return reportFile.getId();
    }

    @Override
    public ResponseEntity<Resource> downloadFileById(int id) throws FileNotFoundException {
        ReportFile reportFile = reportRepository.findById(id).orElseThrow(FileNotFoundException::new);
        String json = reportFile.getJson();
        Resource resource = new ByteArrayResource(json.getBytes());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"report.json\"")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(resource);
    }
}