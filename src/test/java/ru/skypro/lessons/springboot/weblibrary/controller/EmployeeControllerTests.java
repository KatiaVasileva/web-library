package ru.skypro.lessons.springboot.weblibrary.controller;

import com.fasterxml.jackson.core.type.TypeReference;
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
import org.springframework.test.web.servlet.MockMvc;
import ru.skypro.lessons.springboot.weblibrary.dto.CreateEmployee;
import ru.skypro.lessons.springboot.weblibrary.dto.EmployeeDTO;
import ru.skypro.lessons.springboot.weblibrary.model.Position;
import ru.skypro.lessons.springboot.weblibrary.model.projections.EmployeeFullInfo;
import ru.skypro.lessons.springboot.weblibrary.repository.PositionRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@WithMockUser(username="admin",roles={"USER","ADMIN"})
public class EmployeeControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private final Faker faker = new Faker();

    @Test
    public void givenListOfEmployees_whenFindTotalSalary_thenGetTheSumOfTotalSalary() throws Exception {
        List<EmployeeDTO> employeeDTOS = addAllEmployees();
        int expectedSum = employeeDTOS.stream()
                .mapToInt(EmployeeDTO::getSalary)
                .sum();
        String expectedString = "Total salary sum equal to " + expectedSum + " rubles was found.";

        mockMvc.perform(get("/employee/salary/sum"))
                .andExpect(result -> {
                    assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
                    assertThat(result.getResponse().getContentAsString())
                            .isEqualTo(expectedString);
                });
    }

    @Test
    public void givenListOfEmployees_whenFindEmployeesWithMinSalary_thenGetListOfEmployeesWithMinSalary() throws Exception {
        List<EmployeeDTO> employeeDTOS = addAllEmployees();
        int minSalary = employeeDTOS.stream()
                .mapToInt(EmployeeDTO::getSalary)
                .min().getAsInt();
        List<EmployeeDTO> expected = employeeDTOS.stream()
                .filter(employeeDTO -> employeeDTO.getSalary() == minSalary)
                .toList();

        String json = mockMvc.perform(get("/employee/salary/min"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        List<EmployeeDTO> actual = objectMapper.readValue(json, new TypeReference<>() {});
        assertThat(actual)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
                .isEqualTo(expected);
    }

    @Test
    public void givenListOfEmployees_whenFindEmployeesWithMaxSalary_thenGetListOfEmployeesWithMaxSalary() throws Exception {
        List<EmployeeDTO> employeeDTOS = addAllEmployees();
        int maxSalary = employeeDTOS.stream()
                .mapToInt(EmployeeDTO::getSalary)
                .max().getAsInt();
        List<EmployeeDTO> expected = employeeDTOS.stream()
                .filter(employeeDTO -> employeeDTO.getSalary() == maxSalary)
                .toList();

        String json = mockMvc.perform(get("/employee/salary/max"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        List<EmployeeDTO> actual = objectMapper.readValue(json, new TypeReference<>() {});
        assertThat(actual)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
                .isEqualTo(expected);
    }

    @Test
    public void givenListOfEmployees_whenFindEmployeesWithHighSalary_thenGetListOfEmployeesWithSalaryHigherThanAverage() throws Exception {
        List<EmployeeDTO> employeeDTOS = addAllEmployees();
        double avrSalary = employeeDTOS.stream()
                .mapToInt(EmployeeDTO::getSalary)
                .average().getAsDouble();
        List<EmployeeDTO> expected = employeeDTOS.stream()
                .filter(employeeDTO -> employeeDTO.getSalary() > avrSalary)
                .toList();

        String json = mockMvc.perform(get("/employee/high-salary"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        List<EmployeeDTO> actual = objectMapper.readValue(json, new TypeReference<>() {});
        assertThat(actual)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
                .isEqualTo(expected);
    }

    @Test
    public void givenListOfEmployees_whenFindEmployeeById_thenItIsFound() throws Exception {
        addAllEmployees();
        int id = 1;

        mockMvc.perform(get("/employee/{id}", id))
                .andExpect(result -> assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value()));
    }

    @Test
    public void givenListOfEmployees_whenFindEmployeesWithSalaryHigherThan_thenGetEmployeesWithThisSalary() throws Exception {
        List<EmployeeDTO> employeeDTOS = addAllEmployees();
        int salary = 80_000;
        List<EmployeeDTO> expected = employeeDTOS.stream()
                .filter(employeeDTO -> employeeDTO.getSalary() > salary)
                .toList();

        String json = mockMvc.perform(get("/employee/salaryHigherThan?salary=80000"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        List<EmployeeDTO> actual = objectMapper.readValue(json, new TypeReference<>() {});
        assertThat(actual)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
                .isEqualTo(expected);
    }

    @Test
    public void givenListOfEmployees_whenFindEmployeesWithIncorrectSalary_thenGetBadRequest() throws Exception {
        addAllEmployees();

        mockMvc.perform(get("/employee/salaryHigherThan?salary="))
                .andExpect(status().isBadRequest());
        mockMvc.perform(get("/employee/salaryHigherThan?salary=qwe"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void givenListOfEmployees_whenFindEmployeeFullInfoWithMaxSalary_thenGetEmployeeFullInfo() throws Exception {
        addAllEmployees();
        List<EmployeeFullInfo> employeeFullInfoList = new ArrayList<>();

        mockMvc.perform(get("/employee/withHighestSalary")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(result -> {
                    assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
                    employeeFullInfoList.addAll(
                            objectMapper.readValue(
                                    result.getResponse().getContentAsString(),
                                    new TypeReference<>() {
                                    }));
                });

        int maxSalary = employeeFullInfoList.stream()
                .mapToInt(EmployeeFullInfo::getSalary)
                .max().getAsInt();
        List<EmployeeFullInfo> expected = employeeFullInfoList.stream()
                .filter(employeeFullInfo -> employeeFullInfo.getSalary() == maxSalary)
                .toList();
        String json = mockMvc.perform(get("/employee/withHighestSalary"))
                .andReturn().getResponse().getContentAsString();
        List<EmployeeDTO> actual = objectMapper.readValue(json, new TypeReference<>() {});
        assertThat(actual)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
                .isEqualTo(expected);
    }

    @Test
    public void givenListOfEmployees_whenFindEmployeesWithoutDepartment_thenGetAllEmployees() throws Exception {
        List<EmployeeDTO> expectedEmployeeDTOList = addAllEmployees();

        mockMvc.perform(get("/employee?position="))
                .andExpect(result -> {
                    assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
                    String allEmployeesAsString = result.getResponse().getContentAsString();
                    List<EmployeeDTO> actual = objectMapper.readValue(allEmployeesAsString, new TypeReference<>() {});
                    assertThat(actual)
                            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
                            .isEqualTo(expectedEmployeeDTOList);
                });
    }

    @Test
    public void givenListOfEmployees_whenFindEmployeeFullInfoById_thenGetEmployeeFullInfo() throws Exception {
        addAllEmployees();
        int id = 1;

        mockMvc.perform(get("/employee/{id}/fullInfo", id))
                .andExpect(result -> assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value())
                );
    }

    @Test
    public void givenListOfEmployees_whenFindEmployeeFullInfoByWrongId_thenNotFound() throws Exception {
        addAllEmployees();
        int id = 55;

        mockMvc.perform(get("/employee/{id}/fullInfo", id))
                .andExpect(result -> assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    public void givenListOfEmployees_whenFindEmployeeWithPage_GetEmployeesOnGivenPage() throws Exception {
        List<EmployeeDTO> employeeDTOS = addAllEmployees();
        String name = employeeDTOS.get(2).getName();

        mockMvc.perform(get("/employee/page?page=1&size=2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value(name));
    }

    @Test
    public void givenListOfEmployees_whenFindEmployeeWithoutPage_GetEmployeesOnPage0() throws Exception {
        List<EmployeeDTO> employeeDTOS = addAllEmployees();
        String name = employeeDTOS.get(0).getName();

        mockMvc.perform(get("/employee/page?page=&size=3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0].name").value(name));
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

    public List<EmployeeDTO> addAllEmployees() {
        List<CreateEmployee> employeeList = Stream.generate(this::generateEmployee)
                .limit(5).toList();
        employeeList.forEach(employee -> {
            try {
                addEmployee(employee);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        return employeeList.stream().map(CreateEmployee::toEmployee)
                .map(EmployeeDTO::fromEmployee).toList();
    }
}
