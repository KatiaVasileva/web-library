package ru.skypro.lessons.springboot.weblibrary.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import ru.skypro.lessons.springboot.weblibrary.model.Report;
import ru.skypro.lessons.springboot.weblibrary.repository.ReportRepository;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static ru.skypro.lessons.springboot.weblibrary.constants.ReportServiceImplTestConstants.*;

@ExtendWith(MockitoExtension.class)
public class ReportServiceImplTest {

    @Mock
    ReportRepository repositoryMock;

    @InjectMocks
    private ReportServiceImpl out;

    @Test
    @DisplayName("Return ID of created report")
    public void shouldReturnReportIdWhenCreateReportIsCalled() throws IOException {
        when(repositoryMock.getReport())
                .thenReturn(REPORT_ENTRY_LIST);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(REPORT_ENTRY_LIST);
        Report report = new Report(json);
        when(repositoryMock.save(report))
                .thenReturn(report);
        assertEquals(report.getId(), out.createReport());
    }

    @ParameterizedTest
    @DisplayName("Return file with report by ID")
    @MethodSource("provideParamsForReportIdTest")
    public void shouldReturnFileWhenDownloadFileByIdIsCalled(int reportId,
                                                             Report report) throws FileNotFoundException {
        when(repositoryMock.findById(reportId))
                .thenReturn(Optional.of(report));
        String json = report.getJson();
        Resource resource = new ByteArrayResource(json.getBytes());
        ResponseEntity<Resource> file = ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"report.json\"")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(resource);
        assertEquals(file, out.downloadFileById(reportId));
    }

    public static Stream<Arguments> provideParamsForReportIdTest() {
        return Stream.of(
                Arguments.of(REPORT_ID_1, REPORT_1),
                Arguments.of(REPORT_ID_2, REPORT_2)
        );
    }
}
