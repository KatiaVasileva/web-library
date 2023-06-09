package ru.skypro.lessons.springboot.weblibrary.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.skypro.lessons.springboot.weblibrary.model.projections.ReportEntry;
import ru.skypro.lessons.springboot.weblibrary.model.Report;
import ru.skypro.lessons.springboot.weblibrary.repository.ReportRepository;

import java.io.*;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService{

    private static final Logger LOGGER = LoggerFactory.getLogger(ReportServiceImpl.class);

    private final ReportRepository reportRepository;

    public ReportServiceImpl(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @Override
    public int createReport() throws IOException {
        List<ReportEntry> reportEntries = reportRepository.getReport();
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(reportEntries);
        Report report = new Report(json);
        reportRepository.save(report);
        int reportId = report.getId();
        LOGGER.info("Report with id = {} was created: {}", reportId, report);
        LOGGER.debug("Database was updated");
        return reportId;
    }

    @Override
    public ResponseEntity<Resource> downloadFileById(int id) throws FileNotFoundException {
        try {
            Report report = reportRepository.findById(id).orElseThrow(FileNotFoundException::new);
            String json = report.getJson();
            Resource resource = new ByteArrayResource(json.getBytes());
            ResponseEntity<Resource> file = ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"report.json\"")
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(resource);
            LOGGER.info("File was find by id = {}: {}", id, file);
            return file;
        } catch (FileNotFoundException e) {
            LOGGER.error("There is no report with id = " + id, e);
            throw new FileNotFoundException();
        }
    }
}
