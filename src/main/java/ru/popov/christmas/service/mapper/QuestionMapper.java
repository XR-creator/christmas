package ru.popov.christmas.service.mapper;

import org.mapstruct.Mapper;
import ru.popov.christmas.dto.QuestionDTO;
import ru.popov.christmas.model.Question;

@Mapper(componentModel="spring")
public interface QuestionMapper extends AbstractMapper<Question, QuestionDTO>{
}
