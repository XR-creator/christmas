package ru.popov.christmas.service.mapper;

import org.mapstruct.Mapper;
import ru.popov.christmas.dto.CardDTO;
import ru.popov.christmas.model.Card;

@Mapper(componentModel="spring")
public interface CardMapper extends AbstractMapper<Card, CardDTO> {
}
