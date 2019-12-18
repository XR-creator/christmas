package ru.popov.christmas.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.popov.christmas.dto.CardDTO;
import ru.popov.christmas.dto.UserDTO;
import ru.popov.christmas.dto.UserStatsDTO;
import ru.popov.christmas.model.Card;
import ru.popov.christmas.model.User;

import java.util.List;

@Mapper(componentModel="spring")
public interface UserMapper extends AbstractMapper<User, UserDTO> {

    List<UserStatsDTO> toStats(List<User> user);

    @Mapping(source = "cardType.title", target = "cardTypeDescription")
    CardDTO toCardTypeDto(Card card);
}
