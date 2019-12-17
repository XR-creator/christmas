package ru.popov.christmas.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.popov.christmas.model.CardType;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class QuestionDTO extends BaseDTO implements Serializable {

    private String text;
    private CardType type;
    private Boolean used = Boolean.FALSE;
}
