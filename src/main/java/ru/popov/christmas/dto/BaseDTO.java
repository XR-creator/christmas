package ru.popov.christmas.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public abstract class BaseDTO {

    protected Long id;

    protected Date create;
}
