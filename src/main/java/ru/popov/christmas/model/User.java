package ru.popov.christmas.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(schema = "public")
public class User extends BaseEntity {

    @Column
    private String name;

    @Column
    private String token;

    @Column
    private String email;

    @Column
    private Boolean isAdmin;

    @Column
    private Integer count;

    @OneToOne
    private Card card;
}
