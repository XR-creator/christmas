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
public class Question extends BaseEntity {

    @Column
    private String text;

    @Column
    private CardType type;

    @Column
    private Boolean used = Boolean.FALSE;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}
