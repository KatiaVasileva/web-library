package ru.skypro.lessons.springboot.weblibrary.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.skypro.lessons.springboot.weblibrary.model.Position;

@Getter
@Setter
@ToString
@EqualsAndHashCode

public class CreatePosition {

    private String name;

    public Position toPosition() {
        Position position = new Position();
        position.setName(this.getName());
        return position;
    }
}
