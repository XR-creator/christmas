package ru.popov.christmas.service.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.popov.christmas.model.CardType;
import ru.popov.christmas.model.Question;

import java.util.List;

@Repository
public interface QuestionRepository extends CrudRepository<Question, Long> {

    List<Question> findByTypeAndUsedIsFalse(CardType type);
}
