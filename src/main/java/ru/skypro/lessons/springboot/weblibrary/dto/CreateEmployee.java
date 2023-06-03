package ru.skypro.lessons.springboot.weblibrary.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import ru.skypro.lessons.springboot.weblibrary.model.Employee;
import ru.skypro.lessons.springboot.weblibrary.model.Position;

@Getter
@Setter

public class CreateEmployee {

    @NotBlank(message = "Please enter the name. This parameter is mandatory.")
    @NotNull(message = "The name cannot be null.")
    private String name;

    @Positive(message = "The salary should be a positive number.")
    private int salary;

    @Positive(message = "The position number should be a positive number.")
    private int positionNumber;

    public static CreateEmployee fromEmployee(Employee employee) {
        CreateEmployee createEmployee = new CreateEmployee();
        createEmployee.setName(employee.getName());
        createEmployee.setSalary(employee.getSalary());
        createEmployee.setPositionNumber(createEmployee.getPositionNumber());
        return createEmployee;
    }

    public Employee toEmployee() {
        Employee employee = new Employee();
        employee.setName(this.getName());
        employee.setSalary(this.getSalary());
        Position position = new Position();
        position.setId(this.getPositionNumber());
        employee.setPosition(position);
        return employee;
    }
}
