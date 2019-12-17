package ru.popov.christmas.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.popov.christmas.model.CardType;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class CardDTO extends BaseDTO implements Serializable {

    private String title;
    private String description;
    private CardType cardType;
    private String pathIcon;
}
