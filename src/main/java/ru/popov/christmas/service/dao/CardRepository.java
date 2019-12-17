package ru.popov.christmas.service.dao;

import org.springframework.data.repository.CrudRepository;
import ru.popov.christmas.model.Card;
import ru.popov.christmas.model.CardType;

public interface CardRepository extends CrudRepository<Card, Long> {

    Integer countByCardTypeEquals(CardType type);
}
