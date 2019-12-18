package ru.popov.christmas.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class GroupStatsDTO extends StatsDTO implements Serializable {

    public GroupStatsDTO(Long id, String name, Integer count) {
        this.id = id;
        this.name = name;
        this.count = count;
    }
}
