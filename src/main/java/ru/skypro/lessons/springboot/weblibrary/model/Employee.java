package ru.skypro.lessons.springboot.weblibrary.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString

@Entity
@Table(name = "employees")

public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Please enter the name. This parameter is mandatory.")
    @NotNull(message = "The name cannot be null.")
    private String name;

    @Positive(message = "The salary should be a positive number.")
    private int salary;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "position_id")
    private Position position;

    public Employee(int id, @NotNull String name, int salary) {
        this.id = id;
        this.name = name;
        this.salary = salary;
    }
}
