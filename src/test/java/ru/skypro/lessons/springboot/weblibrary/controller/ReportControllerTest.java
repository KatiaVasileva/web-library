package ru.skypro.lessons.springboot.weblibrary.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import ru.skypro.lessons.springboot.weblibrary.dto.CreateEmployee;
import ru.skypro.lessons.springboot.weblibrary.model.Position;
import ru.skypro.lessons.springboot.weblibrary.repository.PositionRepository;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
public class ReportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private final Faker faker = new Faker();

    @Test
    public void givenListOfEmployees_whenCreateReport_thenReportId() throws Exception {
        addAllEmployees();

        mockMvc.perform(post("/report")
                .contentType("/"))
                .andExpect(result -> assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value()));
    }

    @Test
    public void givenListOfReports_whenDownloadFileById_thenDownload() throws Exception {
        addAllEmployees();
        mockMvc.perform(post("/report")
                        .contentType("/"))
                .andExpect(result -> {
                    assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
                    assertThat(result.getResponse().getContentAsString()).isEqualTo("1");
                });

        mockMvc.perform(get("/report/1"))
                .andExpect(result -> assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value()));
    }

    @Test
    public void givenListOfReports_whenDownloadFileWrongId_thenNotFound() throws Exception {
        addAllEmployees();

        mockMvc.perform(post("/report")
                        .contentType("/"))
                .andExpect(result -> assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value()));

        mockMvc.perform(get("/report/10"))
                .andExpect(result -> assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value()));
    }

    private CreateEmployee generateEmployee() {
        CreateEmployee createEmployee = new CreateEmployee();
        createEmployee.setName(faker.name().fullName());
        createEmployee.setSalary(faker.random().nextInt(50_000, 200_000));
        createEmployee.setPositionNumber(generatePosition().getId());
        return createEmployee;
    }

    private Position generatePosition() {
        Position position = new Position();
        position.setName(faker.company().profession());
        positionRepository.save(position);
        return position;
    }

    public void addEmployee(CreateEmployee employee) throws Exception {
        mockMvc.perform(post("/admin/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employee)))
                .andExpect(result -> assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value()));
    }

    public void addAllEmployees() {
        List<CreateEmployee> employeeList = Stream.generate(this::generateEmployee)
                .limit(5).toList();
        employeeList.forEach(employee -> {
            try {
                addEmployee(employee);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}



