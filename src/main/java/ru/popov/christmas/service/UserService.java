package ru.popov.christmas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;
import ru.popov.christmas.dto.UserDTO;
import ru.popov.christmas.model.User;
import ru.popov.christmas.model.UserGoogle;
import ru.popov.christmas.service.mapper.UserMapper;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService extends AbstractService {

    @Autowired
    private UserMapper userMapper;

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

        User user = userRepository.findByEmail(userGoogle.getEmail());
        if (user == null) {
            user = new User();
        }

        user.setToken(userGoogle.getId());
        user.setEmail(userGoogle.getEmail());
        user.setName(userGoogle.getName());
        if (user.getCard() == null) {
            user.setCard(cardService.generateCard());
        }
        User save = userRepository.save(user);

        return userMapper.toDto(save);
    }

    @Transactional
    public List<UserDTO> getAllWithoutLead(OAuth2AuthenticationToken authentication) {
        getUser(authentication);

        return userMapper.toDto(userRepository.findAllWithoutTeamLead());
    }

    public User findById(Long userId) {
        return userRepository.findById(userId).get();
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public List<User> getAll() {
        List<User> result = new ArrayList<>();

        userRepository.findAll().forEach(userItem -> result.add(userItem));

        return result;
    }

    public List<User> getAllTeamLeads() {
        return userRepository.getAllTeamLeads();
    }

    public Integer getSumCountUsersByLead(Long leadId) {
        return userRepository.getSumCountUsersByLead(leadId);
    }
}
