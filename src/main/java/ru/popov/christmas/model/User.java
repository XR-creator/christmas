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
    private Boolean isAdmin = false;

    @Column
    private Integer count = 0;

    @Column
    private Integer groupCount = 0;

    @OneToOne
    private Card card;

    @ManyToOne
    private User parent;
}
