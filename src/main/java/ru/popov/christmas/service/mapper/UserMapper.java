package ru.popov.christmas.service.mapper;

import org.mapstruct.Mapper;
import ru.popov.christmas.dto.UserDTO;
import ru.popov.christmas.model.User;

@Mapper(componentModel="spring")
public interface UserMapper extends AbstractMapper<User, UserDTO> {
}
