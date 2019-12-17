package ru.popov.christmas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.popov.christmas.dto.UserDTO;
import ru.popov.christmas.service.AuthService;
import ru.popov.christmas.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @GetMapping
    public UserDTO getUser(OAuth2AuthenticationToken authentication) {
        return userService.mergeUser(authentication);
    }

    @GetMapping("/info")
    public String infoByCurrUser(OAuth2AuthenticationToken authentication) {
        return authService.getLoginInfo(authentication).toString();
    }
}
