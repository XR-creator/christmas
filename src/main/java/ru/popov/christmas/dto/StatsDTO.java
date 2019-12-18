package ru.popov.christmas.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class StatsDTO extends BaseDTO {

    protected String name;
    protected Integer count;
    protected Integer position;
}
