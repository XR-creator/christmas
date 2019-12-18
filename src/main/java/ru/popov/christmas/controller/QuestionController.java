package ru.popov.christmas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.popov.christmas.dto.QuestionDTO;
import ru.popov.christmas.model.CardType;
import ru.popov.christmas.service.QuestionService;

@RestController
@RequestMapping("/api/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping
    public QuestionDTO getQuestion(@RequestParam CardType cardType, OAuth2AuthenticationToken authentication) {
        return questionService.getQuestionNext(cardType, authentication);
    }
}
