package ru.popov.christmas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.popov.christmas.model.Card;
import ru.popov.christmas.model.CardType;
import ru.popov.christmas.service.dao.CardRepository;

@Service
public class CardService {

    @Autowired
    private CardRepository cardRepository;

    public Card generateCard() {
        Integer leadCount = cardRepository.countByCardTypeEquals(CardType.TEAM_LEAD);

        if (leadCount >= 4) {
            return
        }

        return null;
    }
}
