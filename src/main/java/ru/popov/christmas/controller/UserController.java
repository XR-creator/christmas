package ru.popov.christmas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.*;
import ru.popov.christmas.dto.Answer;
import ru.popov.christmas.dto.UserDTO;
import ru.popov.christmas.model.CardType;
import ru.popov.christmas.model.Question;
import ru.popov.christmas.model.User;
import ru.popov.christmas.service.AuthService;
import ru.popov.christmas.service.QuestionService;
import ru.popov.christmas.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @Autowired
    private QuestionService questionService;

    @GetMapping
    public UserDTO getUser(OAuth2AuthenticationToken authentication) {
        return userService.mergeUser(authentication);
    }

    @GetMapping("/withoutLead")
    public List<UserDTO> getUserWithoutCurUser(OAuth2AuthenticationToken authentication) {
        return userService.getAllWithoutCurUser(authentication);
    }

    @GetMapping("/info")
    public String infoByCurrUser(OAuth2AuthenticationToken authentication) {
        return authService.getLoginInfo(authentication).toString();
    }

    @PutMapping(value = "/incCount")
    public void incrementUserCount(
            @RequestBody Answer answer,
            OAuth2AuthenticationToken authentication) {
        User user = userService.getUser(authentication);
        if (user.getCard() == null) {
            throw new RuntimeException("not found card");
        }

        if (user.getCard().getCardType() != CardType.TEAM_LEAD) {
            throw new RuntimeException("not team lead card");
        }

        User childUser = userService.findById(answer.getUserId());
        if (childUser == null) {
            throw new RuntimeException("not found user selected");
        }

        Question question = questionService.findById(answer.getQuestionId());
        if (question == null) {
            throw new RuntimeException("not found question selected");
        }

        childUser.setCount(childUser.getCount() + 1);
        if (childUser.getParent() == null
                && user.getCard().getCardType() == CardType.TEAM_LEAD) {
            childUser.setParent(user);
        }

        userService.save(childUser);
    }
}
