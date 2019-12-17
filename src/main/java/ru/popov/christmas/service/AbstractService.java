package ru.popov.christmas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import ru.popov.christmas.model.User;
import ru.popov.christmas.model.UserGoogle;
import ru.popov.christmas.service.dao.UserRepository;

import javax.transaction.Transactional;

public abstract class AbstractService {

    @Autowired
    protected AuthService authService;

    @Autowired
    protected UserRepository userRepository;

    @Transactional
    public User getUser(OAuth2AuthenticationToken authentication) {
        UserGoogle userGoogle = authService.getLoginInfo(authentication);
        if (userGoogle == null) {
            return null;
        }

        if (userGoogle.getEmail() == null) {
            return null;
        }

        User user = userRepository.findByEmail(userGoogle.getEmail());

        if (user == null) {
            throw new SecurityException("not found user");
        }

        return user;
    }
}
