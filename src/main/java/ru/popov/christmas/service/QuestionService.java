package ru.popov.christmas.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import ru.popov.christmas.dto.QuestionDTO;
import ru.popov.christmas.model.CardType;
import ru.popov.christmas.model.Question;
import ru.popov.christmas.model.User;
import ru.popov.christmas.service.dao.QuestionRepository;
import ru.popov.christmas.service.mapper.QuestionMapper;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Random;

@Service
public class QuestionService extends AbstractService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuestionMapper questionMapper;

    @Transactional
    public QuestionDTO getQuestionNext(CardType cardType, OAuth2AuthenticationToken authentication) {
        User user = getUser(authentication);

        if (user.getCard() == null) {
            throw new RuntimeException("card not found - user: " + user.getEmail());
        }

        if (cardType == null) {
            throw new RuntimeException("card type is empty - user: " + user.getEmail() + "/cardId: " + user.getCard().getId());
        }

        Integer max = questionRepository.countByTypeAndUsedIsFalse(cardType);
        if (max == 0) {
            return null;
        }

        int index = new Random().nextInt(max);
        List<Question> questions = questionRepository.findByTypeAndUsedIsFalse(cardType, PageRequest.of(index, 1));

        Question questionResult = questions.get(0);
        if (questionResult != null) {
            questionResult.setUsed(true);
            questionRepository.save(questionResult);
        }

        return !CollectionUtils.isEmpty(questions) ? questionMapper.toDto(questionResult) : null;
    }

    public Question findById(Long questionId) {
        return questionRepository.findById(questionId).get();
    }
}
