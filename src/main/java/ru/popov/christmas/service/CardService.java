package ru.popov.christmas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import ru.popov.christmas.model.Card;
import ru.popov.christmas.model.CardType;
import ru.popov.christmas.service.dao.CardRepository;

import java.util.List;
import java.util.Random;

@Service
public class CardService extends AbstractService {

    protected static final int MAX_ROLE_CARDS = 6;

    @Autowired
    private CardRepository cardRepository;

    public Card generateCard() {
        List<Card> cards = cardRepository.findAllByCardTypeEqualsAndUserIsNull(CardType.TEAM_LEAD);
        List<Card> otherCards = cardRepository.findAllByCardTypeIsNotAndUserIsNull(
                CardType.TEAM_LEAD,
                PageRequest.of(0, MAX_ROLE_CARDS - cards.size()));
        cards.addAll(otherCards);

        int index = new Random().nextInt(cards.size());
        System.out.println("random = " + index);
        return !CollectionUtils.isEmpty(cards) ? cards.get(index) : null;
    }
}
