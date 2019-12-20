package ru.popov.christmas.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class UserStatsDTO extends StatsDTO implements Serializable {

    public UserStatsDTO(String name, Integer count) {
        this.name = name;
        this.count = count;
    }
}
