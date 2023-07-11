package ru.skypro.lessons.springboot.weblibrary.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString

@Entity
@Table(name = "positions")
public class Position {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    @NotBlank(message = "Please enter the position. This parameter is mandatory.")
    @NotNull(message = "The position cannot be null.")
    private String name;

    @OneToMany(mappedBy = "position", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Employee> employees;

    public Position(int id) {
        this.id = id;
    }

    public Position(String name) {
        this.name = name;
    }

    public Position(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
