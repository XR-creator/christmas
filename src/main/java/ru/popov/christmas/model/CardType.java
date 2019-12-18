package ru.popov.christmas.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.io.Serializable;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum CardType implements Serializable {

    TESTER("Тестировщик"),
    DEVELOPER("Разработчик"),
    ANALYTIC("Аналитик"),
    TEAM_LEAD("Тим лид");

    private String title;

    CardType(String title) {
        this.title = title;
    }
}
