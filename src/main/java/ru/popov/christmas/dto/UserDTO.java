package ru.popov.christmas.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO extends BaseDTO implements Serializable {

    private String name;
    private String token;
    private String email;
    private CardDTO card;
}
