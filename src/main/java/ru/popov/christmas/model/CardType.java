package ru.popov.christmas.model;

import lombok.Getter;

import java.io.Serializable;

@Getter
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
