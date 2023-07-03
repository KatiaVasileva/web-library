package ru.skypro.lessons.springboot.weblibrary.constants;

import ru.skypro.lessons.springboot.weblibrary.model.Report;
import ru.skypro.lessons.springboot.weblibrary.model.projections.ReportEntry;

import java.util.List;

public class ReportServiceImplTestConstants {

    public static final List<ReportEntry> REPORT_ENTRY_LIST = List.of
            (new ReportEntry("Developer", 10, 154_000, 78_000, 100_000),
                    (new ReportEntry("Engineer", 8, 108_000, 75_000, 91_000)));
    public static final int REPORT_ID_1 = 1;
    public static final int REPORT_ID_2 = 2;
    public static final String REPORT_JSON_1 = "[{\"position\":\"Engineer\",\"employeeQuantity\":8,\"maxSalary\":163000,\"minSalary\":65000,\"avrSalary\":102375.0}," +
            "{\"position\":\"Manager\",\"employeeQuantity\":7,\"maxSalary\":104000,\"minSalary\":49400,\"avrSalary\":77571.42857142857}]";
    public static final String REPORT_JSON_2 = "[{\"position\":\"Manager\",\"employeeQuantity\":10,\"maxSalary\":170000,\"minSalary\":65000,\"avrSalary\":102375.0}," +
            "{\"position\":\"Accountant\",\"employeeQuantity\":7,\"maxSalary\":104000,\"minSalary\":49400,\"avrSalary\":77571.42857142857}]";
    public static final Report REPORT_1 = new Report(REPORT_ID_1, REPORT_JSON_1);
    public static final Report REPORT_2 = new Report(REPORT_ID_2, REPORT_JSON_2);
}
