package ru.skypro.lessons.springboot.weblibrary.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import ru.skypro.lessons.springboot.weblibrary.dto.CreateEmployee;
import ru.skypro.lessons.springboot.weblibrary.dto.EmployeeDTO;
import ru.skypro.lessons.springboot.weblibrary.model.Employee;
import ru.skypro.lessons.springboot.weblibrary.model.Position;
import ru.skypro.lessons.springboot.weblibrary.repository.EmployeeRepository;
import ru.skypro.lessons.springboot.weblibrary.repository.PositionRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@WithMockUser(username = "admin", roles = {"ADMIN", "USER"})
public class AdminEmployeeControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private EmployeeRepository employeeRepository;

        @Autowired
        private PositionRepository positionRepository;

        @Autowired
        private ObjectMapper objectMapper;

        private final Faker faker = new Faker();

        @AfterEach
        public void afterEach() {
            employeeRepository.deleteAll();
            positionRepository.deleteAll();
        }

        @Test
        public void addEmployeeTest() throws Exception {
            CreateEmployee createEmployee = generateEmployee();
            addEmployee(createEmployee);

            EmployeeDTO employeeDTO = EmployeeDTO.fromEmployee(createEmployee.toEmployee());
            List<EmployeeDTO> expected = List.of(employeeDTO);
            List<EmployeeDTO> actual = getAllTest();
            assertThat(actual)
                    .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "positionNumber")
                    .isEqualTo(expected);
        }

        @Test
        void givenNoEmployeesInDatabase_whenEmployeeAddedWithNullOrEmptyOrInvalidParameters_thenBadRequest() throws Exception {
            CreateEmployee createEmployee1 = generateEmployee();

            createEmployee1.setName("");
            badRequestResponseWhenAddWithInvalidParameters(createEmployee1);

            createEmployee1.setName(null);
            badRequestResponseWhenAddWithInvalidParameters(createEmployee1);

            CreateEmployee createEmployee2 = generateEmployee();
            createEmployee2.setSalary(-10);
            badRequestResponseWhenAddWithInvalidParameters(createEmployee2);

            CreateEmployee createEmployee3 = generateEmployee();
            createEmployee3.setPositionNumber(0);
            badRequestResponseWhenAddWithInvalidParameters(createEmployee3);
        }

        public void badRequestResponseWhenAddWithInvalidParameters(CreateEmployee createEmployee) throws Exception {
            mockMvc.perform(post("/admin/employee")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(createEmployee)))
                    .andExpect(result -> assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value()));
        }

        @Test
        public void givenListOfEmployees_whenEditEmployeeById_thenItChangedInDatabase() throws Exception {
            addAllEmployees();
            CreateEmployee updateEmployee = generateEmployee();
            int id = 1;

            mockMvc.perform(put("/admin/employee/{id}", id)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(updateEmployee)))
                    .andExpect(result -> assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value()));

            mockMvc.perform(get("/employee/{id}/fullInfo", id))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name").value(updateEmployee.getName()))
                    .andExpect(jsonPath("$.salary").value(updateEmployee.getSalary()));
        }

        @Test
        public void givenListOfEmployees_whenEditEmployeeByWrongId_thenNotFound() throws Exception {
            addAllEmployees();
            CreateEmployee updateEmployee = generateEmployee();
            int id = 11;

            mockMvc.perform(put("/admin/employee/{id}", id)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(updateEmployee)))
                    .andExpect(status().isNotFound());
        }

    @Test
    public void givenListOfEmployees_whenEditEmployeeWithInvalidParameters_thenBadRequest() throws Exception {
        addAllEmployees();
        CreateEmployee updateEmployee = generateEmployee();
        int id = 1;

        updateEmployee.setName("");
        badRequestResponseWhenEditWithInvalidParameters(updateEmployee, id);

        updateEmployee.setName(null);
        badRequestResponseWhenEditWithInvalidParameters(updateEmployee, id);

        CreateEmployee updateEmployee1 = generateEmployee();
        updateEmployee1.setSalary(-10);
        badRequestResponseWhenEditWithInvalidParameters(updateEmployee1, id);

        CreateEmployee updateEmployee2 = generateEmployee();
        updateEmployee2.setPositionNumber(0);
        badRequestResponseWhenEditWithInvalidParameters(updateEmployee2, id);
    }

    public void badRequestResponseWhenEditWithInvalidParameters(CreateEmployee updateEmployee, int id) throws Exception {
        mockMvc.perform(put("/admin/employee/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateEmployee)))
                .andExpect(result -> assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value()));
    }

        @Test
        public void givenListOfEmployees_whenDeleteEmployeeById_thenDeleted() throws Exception {
            addAllEmployees();
            int id = 2;

            mockMvc.perform(delete("/admin/employee/{id}", id))
                    .andExpect(result -> assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value()));

            mockMvc.perform(get("/employee/{id}", id))
                    .andExpect(result -> assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value()));
        }

        @Test
        public void givenListOfEmployees_whenDeleteEmployeeByWrongId_thenNotFound() throws Exception {
            addAllEmployees();
            int id = 11;

            mockMvc.perform(delete("/admin/employee/{id}", id))
                    .andExpect(result -> assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value()));
        }

        @Test
        public void uploadFileTest() throws Exception {
            List<Employee> expected = objectMapper.readValue(
                    ru.skypro.lessons.springboot.weblibrary.controller.AdminEmployeeControllerTest.class.getResourceAsStream("employees.json"),
                    new TypeReference<>() {
                    });

            List<EmployeeDTO> expectedDTO = expected.stream()
                    .map(EmployeeDTO::fromEmployee)
                    .toList();

            MockMultipartFile mockMultipartFile = new MockMultipartFile(
                    "employees",
                    "employees.json",
                    MediaType.APPLICATION_JSON_VALUE,
                    ru.skypro.lessons.springboot.weblibrary.controller.AdminEmployeeControllerTest.class.getResourceAsStream("employees.json")
            );

            mockMvc.perform(multipart("/admin/employee/upload")
                            .file(mockMultipartFile)
                            .contentType(MediaType.MULTIPART_FORM_DATA_VALUE))
                    .andExpect(result -> {
                                assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
                                List<EmployeeDTO> actual = getAllTest();
                                assertThat(actual)
                                        .hasSize(3)
                                        .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
                                        .containsExactlyInAnyOrderElementsOf(expectedDTO);
                            }
                    );
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

        public List<EmployeeDTO> getAllTest() throws Exception {
            List<EmployeeDTO> employeeDTO = new ArrayList<>();

            mockMvc.perform(get("/employee/total-employees")
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(result -> {
                        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
                        employeeDTO.addAll(
                                objectMapper.readValue(
                                        result.getResponse().getContentAsString(),
                                        new TypeReference<>() {
                                        }));
                    });
            return employeeDTO;
        }

}
