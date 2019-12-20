package ru.popov.christmas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.*;
import ru.popov.christmas.dto.AnswerDTO;
import ru.popov.christmas.dto.SetCountDTO;
import ru.popov.christmas.dto.UserDTO;
import ru.popov.christmas.model.CardType;
import ru.popov.christmas.model.Question;
import ru.popov.christmas.model.User;
import ru.popov.christmas.service.AuthService;
import ru.popov.christmas.service.QuestionService;
import ru.popov.christmas.service.StatisticsService;
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

    @Autowired
    private StatisticsService statisticsService;

    @GetMapping
    public UserDTO getUser(OAuth2AuthenticationToken authentication) {
        return userService.mergeUser(authentication);
    }

    @GetMapping("/withoutLead")
    public List<UserDTO> getUserWithoutCurUser(OAuth2AuthenticationToken authentication) {
        return userService.getAllWithoutCurUser(authentication);
    }

    @GetMapping("/withoutAdmin")
    public List<UserDTO> getUserWithoutAdmin(OAuth2AuthenticationToken authentication) {
        return userService.getAllWithoutAdmin(authentication);
    }

    @GetMapping("/info")
    public String infoByCurrUser(OAuth2AuthenticationToken authentication) {
        return authService.getLoginInfo(authentication).toString();
    }

    @PostMapping(value = "/incCount")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public UserDTO incrementUserCount(
            @RequestBody AnswerDTO answer,
            OAuth2AuthenticationToken authentication) {
        User user = userService.getUser(authentication);
        if (user.getCard() == null) {
            throw new RuntimeException("not found card");
        }

        if (user.getCard().getCardType() != CardType.TEAM_LEAD
                && !user.getIsAdmin()) {
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

        return userService.findByIdtoDTO(childUser.getId());
    }

    @PostMapping("/edit/count")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public UserDTO setUserCount(
            @RequestBody SetCountDTO userCount,
            OAuth2AuthenticationToken authentication) {
        checkAdminCount(userCount, authentication);

        User user = userService.findById(userCount.getId());
        user.setCount(userCount.getCount());
        userService.save(user);

        return userService.findByIdtoDTO(user.getId());
    }

    @PostMapping("/edit/group/count")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public UserDTO setGroupCount(
            @RequestBody SetCountDTO groupCount,
            OAuth2AuthenticationToken authentication) {
        checkAdminCount(groupCount, authentication);

        User user = userService.findById(groupCount.getId());
        user.setGroupCount(groupCount.getCount());
        userService.save(user);

        return userService.findByIdtoDTO(user.getId());
    }

    private boolean checkAdminCount(@RequestBody SetCountDTO userCount, OAuth2AuthenticationToken authentication) {
        checkAdmin(authentication);

        if (userCount.getId() == null) {
            throw new RuntimeException("user id is empty");
        }
        if (userCount.getCount() == null) {
            throw new RuntimeException("user count is empty");
        }

        return true;
    }

    private boolean checkAdmin(OAuth2AuthenticationToken authentication) {
        UserDTO currUser = getUser(authentication);

        if (!currUser.getIsAdmin()) {
            throw new RuntimeException("you is not admin");
        }
        return true;
    }
}
