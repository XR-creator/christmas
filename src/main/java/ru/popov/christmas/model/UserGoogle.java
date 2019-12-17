package ru.popov.christmas.model;


import lombok.*;

import java.io.Serializable;
import java.net.URI;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserGoogle implements Serializable {

    private String id;

    private String fullName;

    private String name;

    private String family;

    private URI picture;

    private String email;

    private Boolean isEmailVerify;

    private String locate;
}
