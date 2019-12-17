package ru.popov.christmas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;
import ru.popov.christmas.dto.UserDTO;
import ru.popov.christmas.model.Card;
import ru.popov.christmas.model.User;
import ru.popov.christmas.model.UserGoogle;
import ru.popov.christmas.service.dao.UserRepository;
import ru.popov.christmas.service.mapper.UserMapper;

import javax.transaction.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AuthService authService;

    @Autowired
    private CardService cardService;

    @Transactional
    public UserDTO getUser(String name) {
        User user = userRepository.findByName(name);

        return user != null ? userMapper.toDto(user) : null;
    }

    @Transactional
    public UserDTO mergeUser(OAuth2AuthenticationToken authentication) {
        UserGoogle userGoogle = authService.getLoginInfo(authentication);
        if (userGoogle == null) {
            return null;
        }

        if (userGoogle.getEmail() == null) {
            return null;
        }

        User user = userRepository.findByEmail(userGoogle.getEmail());
        if (user == null) {
            user = new User();
//            TODO: Тута выбор роли случайно, но в первых 10 выборках обязательно 4 тимлида
            user.setCard(cardService.generateCard());
        }

        user.setToken(userGoogle.getId());
        user.setEmail(userGoogle.getEmail());
        user.setName(userGoogle.getName());
        User save = userRepository.save(user);

        return userMapper.toDto(save);
    }
}
