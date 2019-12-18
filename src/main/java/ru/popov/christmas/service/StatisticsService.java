package ru.popov.christmas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import ru.popov.christmas.dto.GroupStatsDTO;
import ru.popov.christmas.dto.StatsDTO;
import ru.popov.christmas.dto.UserStatsDTO;
import ru.popov.christmas.model.User;
import ru.popov.christmas.service.mapper.UserMapper;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class StatisticsService extends AbstractService {

    protected static final Comparator<? extends StatsDTO> positionComparator = (o1, o2) -> {
        if (o1.getCount() == null
                && o2.getCount() == null) {
            return 0;
        }

        if (o1.getCount() == null) {
            return 1;
        }

        if (o2.getCount() == null) {
            return -1;
        }

        return o1.getCount().compareTo(o2.getCount());
    };

    protected static final Comparator<StatsDTO> nameComparator = (o1, o2) -> {
        if (o1.getName() == null
                && o2.getName() == null) {
            return 0;
        }

        if (o1.getName() == null) {
            return 1;
        }

        if (o2.getName() == null) {
            return -1;
        }

        return o1.getName().compareTo(o2.getName());
    };
    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @Transactional
    public List<UserStatsDTO> getStatsByUser(OAuth2AuthenticationToken authenticationToken) {
        getUser(authenticationToken);
        List<User> users = userService.getAll();
        List<UserStatsDTO> result = userMapper.toStats(users);
        calcPosition(result);

        return result;
    }

    @Transactional
    public List<GroupStatsDTO> getStatsByGroup(OAuth2AuthenticationToken authenticationToken) {
        getUser(authenticationToken);
        List<GroupStatsDTO> result = new ArrayList<>();
        List<User> users = userService.getAllTeamLeads();
        for (User userItem : users) {
            GroupStatsDTO statItem = new GroupStatsDTO(
                    userItem.getId(),
                    userItem.getName(),
                    userService.getSumCountUsersByLead(userItem.getId()));
            result.add(statItem);
        }
        calcPosition(result);

        return result;
    }

    private void calcPosition(List<? extends StatsDTO> result) {
        if (CollectionUtils.isEmpty(result)) {
            return;
        }

        Collections.sort(result, positionComparator);

        for (int i = 0; i < result.size(); i++) {
            result.get(i).setPosition(i + 1);
        }

        Collections.sort(result, nameComparator);
    }
}
