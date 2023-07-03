package ru.skypro.lessons.springboot.weblibrary.constants;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.lessons.springboot.weblibrary.dto.CreateEmployee;
import ru.skypro.lessons.springboot.weblibrary.model.Employee;
import ru.skypro.lessons.springboot.weblibrary.model.Position;
import ru.skypro.lessons.springboot.weblibrary.model.projections.EmployeeFullInfo;

import java.util.List;

public class EmployeeServiceImplTestConstants {

        public static final List<Employee> EMPLOYEE_LIST = List.of(new Employee(1, "Kate", 190000, new Position(2)),
                new Employee(2, "John", 156000, new Position(1)),
                new Employee(3, "Linda", 98000, new Position(3)));
        public static final String TOTAL_SALARY = "1_000_000";
        public static final List<EmployeeFullInfo> EMPLOYEE_FULL_INFO_LIST = List.of(new EmployeeFullInfo("Kate", 190000, "Developer"),
                new EmployeeFullInfo("John", 145000, "Accountant"));
        public static final CreateEmployee EMPLOYEE = new CreateEmployee("Kate", 190000, 2);
        public static final Employee EMPLOYEE_ID_1 = new Employee(1, "John", 156000, new Position(1));
        public static final Employee EMPLOYEE_ID_2 = new Employee(2, "Kate", 190000, new Position(2));
        public static final int ID_1 = 1;
        public static final int ID_2 = 2;
        public static final int SALARY = 90000;
        public static final String POSITION = "Developer";
        public static final String EMPTY_POSITION = " ";
        public static final List<Employee> EMPLOYEE_LIST_BY_DEPARTMENT = List.of(new Employee(1, "Kate", 190000, new Position(2)),
                new Employee(2, "James", 156000, new Position(2)),
                new Employee(3, "Linda", 98000, new Position(2)));
        public static final EmployeeFullInfo EMPLOYEE_FULL_INFO_1 = new EmployeeFullInfo("John", 145000, "Accountant");
        public static final EmployeeFullInfo EMPLOYEE_FULL_INFO_2 = new EmployeeFullInfo("Henry", 103000, "Manager");
        public static final Integer PAGE_INDEX_1 = 1;
        public static final Integer PAGE_INDEX_DEFAULT = 0;
        public static final Integer PAGE_INDEX_EMPTY = null;
        public static final int SIZE = 2;
        public static final List<Employee> EMPLOYEE_LIST_PAGE_DEFAULT = List.of(new Employee(1, "Kate", 190000, new Position(2)),
                new Employee(2, "John", 156000, new Position(1)));
        public static final List<Employee> EMPLOYEE_LIST_PAGE_1 = List.of(new Employee(3, "Sam", 56000, new Position(4)),
                new Employee(4, "Martha", 78000, new Position(5)));
        public static final String FILE = "[{\"id\": 1, \"name\": \"Kate\", \"salary\": 190000, \"position\": 2}, " +
                "  {\"id\": 2, \"name\": \"John\", \"salary\": 156000, \"position\": 1}," +
                " {\"id\": 3, \"name\": \"Linda\", \"salary\": 98000, \"position\": 3}]";
        public static final MultipartFile MULTIPART_FILE = new MockMultipartFile("employee 2.json", FILE.getBytes());



}
