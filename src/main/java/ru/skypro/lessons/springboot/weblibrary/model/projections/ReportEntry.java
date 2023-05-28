package ru.skypro.lessons.springboot.weblibrary.model.projections;

import lombok.*;

@Getter
@Setter

public class ReportEntry {

    private String position;

    private long employeeQuantity;

    private int maxSalary;

    private int minSalary;

    private double avrSalary;

    public ReportEntry(String position, long employeeQuantity, int maxSalary, int minSalary, double avrSalary) {
        this.position = position;
        this.employeeQuantity = employeeQuantity;
        this.maxSalary = maxSalary;
        this.minSalary = minSalary;
        this.avrSalary = avrSalary;
    }
}
