package ru.skypro.lessons.springboot.weblibrary.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import ru.skypro.lessons.springboot.weblibrary.dto.CreateEmployee;
import ru.skypro.lessons.springboot.weblibrary.dto.EmployeeDTO;
import ru.skypro.lessons.springboot.weblibrary.exceptions.EmployeeNotFoundException;
import ru.skypro.lessons.springboot.weblibrary.model.Employee;
import ru.skypro.lessons.springboot.weblibrary.model.projections.EmployeeFullInfo;
import ru.skypro.lessons.springboot.weblibrary.repository.EmployeeRepository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static ru.skypro.lessons.springboot.weblibrary.constants.EmployeeServiceImplTestConstants.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository repositoryMock;

    @InjectMocks
    private EmployeeServiceImpl out;

    @Test
    @DisplayName("Return list of all employees")
    public void shouldReturnCollectionOfEmployeesWhenFindAllEmployeesIsCalled() {
        when(repositoryMock.findAllEmployees())
                .thenReturn(EMPLOYEE_LIST);
        List<EmployeeDTO> employeeDTOList = EMPLOYEE_LIST.stream()
                .map(EmployeeDTO::fromEmployee)
                .toList();
        assertIterableEquals(employeeDTOList, out.findAllEmployees());
    }

    @Test
    @DisplayName("Return total salary")
    public void shouldReturnTotalSalaryWhenFindTotalSalaryIsCalled() {
        when(repositoryMock.findTotalSalary())
                .thenReturn(TOTAL_SALARY);
        String result = "Total salary sum equal to " +  TOTAL_SALARY + " rubles was found.";
        assertEquals(result, out.findTotalSalary());
    }

    @Test
    @DisplayName("Return employees with minimum salary")
    public void shouldReturnEmployeesWithMinimumSalaryWhenFindEmployeeWithMinSalaryIsCalled() {
        when(repositoryMock.findEmployeesWithMinSalary())
                .thenReturn(EMPLOYEE_LIST);
        List<EmployeeDTO> employeeDTOList = EMPLOYEE_LIST.stream()
                .map(EmployeeDTO::fromEmployee)
                .toList();
        assertEquals(employeeDTOList, out.findEmployeesWithMinSalary());
    }

    @Test
    @DisplayName("Return employees with maximum salary")
    public void shouldReturnEmployeesWithMaximumSalaryWhenFindEmployeeWithMaxSalaryIsCalled() {
        when(repositoryMock.findEmployeesWithMaxSalary())
                .thenReturn(EMPLOYEE_LIST);
        List<EmployeeDTO> employeeDTOList = EMPLOYEE_LIST.stream()
                .map(EmployeeDTO::fromEmployee)
                .toList();
        assertEquals(employeeDTOList, out.findEmployeesWithMaxSalary());
    }

    @Test
    @DisplayName("Return employees with salary higher than average salary")
    public void shouldReturnEmployeesWithHighSalaryWhenFindEmployeesWithHighSalaryIsCalled() {
        when(repositoryMock.findEmployeesWithHighSalary())
                .thenReturn(EMPLOYEE_FULL_INFO_LIST);
        assertEquals(EMPLOYEE_FULL_INFO_LIST, out.findEmployeesWithHighSalary());
    }

    @Test
    @DisplayName("Call repository method to add employee")
    public void shouldCallRepositoryMethodWhenAddEmployeeIsCalled() {
        Employee employee = EMPLOYEE.toEmployee();
        when(repositoryMock.save(eq(employee)))
                .thenReturn(employee);
        out.addEmployee(EMPLOYEE);
        verify(repositoryMock, times(1)).save(employee);
    }

    @Test
    @DisplayName("Call repository method to edit employee")
    public void shouldCallRepositoryMethodWhenEditEmployeeIsCalled() {
        when(repositoryMock.findById(ID_1))
                .thenReturn(Optional.of(EMPLOYEE_ID_1));
        when(repositoryMock.save(eq(EMPLOYEE_ID_1)))
                .thenReturn(EMPLOYEE_ID_1);
        CreateEmployee createEmployee = CreateEmployee.fromEmployee(EMPLOYEE_ID_1);
        out.editEmployee(ID_1, createEmployee);
        verify(repositoryMock, times(1)).save(EMPLOYEE_ID_1);
    }

    @Test
    @DisplayName("Throw exception when employee is not found by ID")
    public void shouldThrowEmployeeNotFoundExceptionWhenIdIsNotFound() {
        when(repositoryMock.findById(any()))
                .thenThrow(EmployeeNotFoundException.class);
        when(repositoryMock.getEmployeeFullInfoById(ID_2))
                .thenThrow(EmployeeNotFoundException.class);

        assertThrows(EmployeeNotFoundException.class,
                () -> out.editEmployee(ID_2, EMPLOYEE));
        assertThrows(EmployeeNotFoundException.class,
                () -> out.getEmployeeById(ID_2));
        assertThrows(EmployeeNotFoundException.class,
                () -> out.deleteEmployeeById(ID_2));
        assertThrows(EmployeeNotFoundException.class,
                () -> out.getEmployeeFullInfoById(ID_2));
    }

    @ParameterizedTest
    @DisplayName("Return employee with given ID")
    @MethodSource("provideParamsForIdTests")
    public void shouldReturnEmployeeDTOWhenFindEmployeeByIdIsCalled(int id, Employee employee) {
        when(repositoryMock.findById(id))
                .thenReturn(Optional.of(employee));
        EmployeeDTO employeeDTO = EmployeeDTO.fromEmployee(employee);
        assertEquals(employeeDTO, out.getEmployeeById(id));
        verify(repositoryMock, only()).findById(id);
    }

    @ParameterizedTest
    @DisplayName("Return employee deleted by ID")
    @MethodSource("provideParamsForIdTests")
    public void shouldReturnEmployeeDTOWhenDeleteEmployeeByIdIsCalled(int id, Employee employee) {
        when(repositoryMock.findById(id))
                .thenReturn(Optional.of(employee));
        EmployeeDTO employeeDTO = EmployeeDTO.fromEmployee(employee);
        assertEquals(employeeDTO, out.deleteEmployeeById(id));
    }

    @Test
    @DisplayName("Return list of employees with salary higher than given salary")
    public void shouldReturnEmployeeDTOListWhenFindBySalaryGreaterThanIsCalled(){
        when(repositoryMock.findBySalaryGreaterThan(SALARY))
                .thenReturn(EMPLOYEE_LIST);
        List<EmployeeDTO> employeeDTOList = EMPLOYEE_LIST.stream()
                .map(EmployeeDTO::fromEmployee)
                .toList();
        assertEquals(employeeDTOList, out.findBySalaryGreaterThan(SALARY));
    }

    @Test
    @DisplayName("Return list of employees (full info) with maximum salary")
    public void shouldReturnEmployeeFullInfoListWithMaxSalaryWhenFindEmployeeFullInfoWithMaxSalaryIsCalled() {
        when(repositoryMock.findEmployeeFullInfoWithMaxSalary())
                .thenReturn(EMPLOYEE_FULL_INFO_LIST);
        assertEquals(EMPLOYEE_FULL_INFO_LIST, out.findEmployeeFullInfoWithMaxSalary());
    }

    @Test
    @DisplayName(value = "Return list of employees in given department")
    public void shouldReturnEmployeeDTOListWhenGetEmployeesByDepartmentIsCalledWithDepartment() {
        when(repositoryMock.getEmployeesByDepartment(eq(POSITION)))
                .thenReturn(EMPLOYEE_LIST_BY_DEPARTMENT);
        List<EmployeeDTO> employeeDTOListByDepartment = EMPLOYEE_LIST_BY_DEPARTMENT.stream()
                .map(EmployeeDTO::fromEmployee)
                .toList();
        assertEquals(employeeDTOListByDepartment, out.getEmployeesByDepartment(POSITION));
    }

    @Test
    @DisplayName(value = "Return list of all employees if department is not mentioned")
    public void shouldReturnEmployeeDTOListWhenGetEmployeesByDepartmentIsCalledWithEmptyDepartment() {
        when(repositoryMock.findAllEmployees())
                .thenReturn(EMPLOYEE_LIST);
        List<EmployeeDTO> employeeDTOList = EMPLOYEE_LIST.stream()
                .map(EmployeeDTO::fromEmployee)
                .toList();
        assertEquals(employeeDTOList, out.getEmployeesByDepartment(EMPTY_POSITION));
    }

    @ParameterizedTest
    @DisplayName("Return full info about employee with given ID")
    @MethodSource("provideParamsForIdTestsWithFullInfo")
    public void shouldReturnEmployeeFullInfoWhenGetEmployeeFullInfoByIdIsCalled(int id,
                                                                                EmployeeFullInfo employeeFullInfo) {
        when(repositoryMock.getEmployeeFullInfoById(id))
                .thenReturn(employeeFullInfo);
        assertEquals(employeeFullInfo, out.getEmployeeFullInfoById(id));
    }

    @ParameterizedTest
    @DisplayName("Return list of employees on given page")
    @MethodSource("provideParamsForPagingTests")
    public void shouldReturnEmployeeDTOListWhenGetEmployeeWithPagingIsCalled(Integer pageIndexShown,
                                                                             int size,
                                                                             Integer pageIndexGiven,
                                                                             List<Employee> employeeList) {
        when(repositoryMock.findAll(eq(PageRequest.of(pageIndexShown, size))))
                .thenReturn(employeeList);
        List<EmployeeDTO> employeeDTOList = employeeList.stream()
                .map(EmployeeDTO::fromEmployee)
                .toList();
        assertEquals(employeeDTOList, out.getEmployeeWithPaging(pageIndexGiven, size));
    }

    @Test
    @DisplayName("Return list of employees added to database from uploaded file")
    public void shouldSaveEmployeesFromFileWhenUploadFileIsCalled() throws IOException {
        when(repositoryMock.saveAll(eq(EMPLOYEE_LIST)))
                .thenReturn(EMPLOYEE_LIST);
        List<EmployeeDTO> employeeDTO = EMPLOYEE_LIST.stream().map(EmployeeDTO::fromEmployee).toList();

        assertEquals(employeeDTO, out.uploadFile(MULTIPART_FILE));
    }

    public static Stream<Arguments> provideParamsForIdTests() {
        return Stream.of(
                Arguments.of(ID_1, EMPLOYEE_ID_1),
                Arguments.of(ID_2, EMPLOYEE_ID_2)
        );
    }

    public static Stream<Arguments> provideParamsForIdTestsWithFullInfo() {
        return Stream.of(
                Arguments.of(ID_1, EMPLOYEE_FULL_INFO_1),
                Arguments.of(ID_2, EMPLOYEE_FULL_INFO_2)
        );
    }

    public static Stream<Arguments> provideParamsForPagingTests() {
        return Stream.of(
                Arguments.of(PAGE_INDEX_1, SIZE, PAGE_INDEX_1, EMPLOYEE_LIST_PAGE_1),
                Arguments.of(PAGE_INDEX_DEFAULT, SIZE, PAGE_INDEX_EMPTY, EMPLOYEE_LIST_PAGE_DEFAULT)
        );
    }
}
