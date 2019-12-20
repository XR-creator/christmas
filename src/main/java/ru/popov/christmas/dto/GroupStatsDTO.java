package ru.popov.christmas.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class GroupStatsDTO extends StatsDTO implements Serializable {

    private Integer personalCount;
    private List<UserStatsDTO> users = new ArrayList<>();

    public GroupStatsDTO(Long id, String name, Integer count, Integer personalCount, List<UserStatsDTO> users) {
        this.id = id;
        this.name = name;
        this.count = count;
        this.personalCount = personalCount;
        this.users = users;
    }
}
