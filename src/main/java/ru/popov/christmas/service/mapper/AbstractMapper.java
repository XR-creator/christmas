package ru.popov.christmas.service.mapper;

import java.util.List;

public interface AbstractMapper<ENTITY, DTO> {

    DTO toDto(ENTITY entity);

    ENTITY toEntity(DTO dto);

    List<DTO> toDto(List<ENTITY> entity);

    List<ENTITY> toEntity(List<DTO> dto);

}
