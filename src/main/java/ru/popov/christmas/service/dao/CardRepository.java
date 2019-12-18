package ru.popov.christmas.service.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import ru.popov.christmas.model.Card;
import ru.popov.christmas.model.CardType;

import java.util.List;

public interface CardRepository extends CrudRepository<Card, Long> {

    Integer countByCardTypeEquals(CardType type);

    List<Card> findAllByCardTypeEqualsAndUserIsNull(CardType type);

    List<Card> findAllByCardTypeIsNotAndUserIsNull(CardType type, Pageable pageable);
}
