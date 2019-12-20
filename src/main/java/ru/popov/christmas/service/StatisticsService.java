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

    protected static final Comparator<StatsDTO> positionComparator = (o1, o2) -> {
        if (o1.getCount() == null
                && o2.getCount() == null) {
            return 0;
        }
        if (o1.getCount() == null) {
            return -1;
        }
        if (o2.getCount() == null) {
            return 1;
        }

        return o2.getCount().compareTo(o1.getCount());
    };

    protected static final Comparator<StatsDTO> nameComparator = (o1, o2) -> {
        if (o1.getName() == null
                && o2.getName() == null) {
            return 0;
        }
        if (o1.getName() == null) {
            return -1;
        }
        if (o2.getName() == null) {
            return 1;
        }

        return o2.getName().compareTo(o1.getName());
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
            List<UserStatsDTO> usersStats = new ArrayList<>();
            userService.getUsersByLead(userItem.getId())
                    .forEach(resultItem -> usersStats.add(new UserStatsDTO(resultItem.getName(), resultItem.getCount())));
            GroupStatsDTO statItem = new GroupStatsDTO(
                    userItem.getId(),
                    userItem.getName(),
                    userService.getSumCountUsersByLead(userItem.getId()),
                    userItem.getGroupCount(),
                    usersStats);
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
            StatsDTO statsItem = result.get(i);
            if (i > 0) {
                Integer beforePosition = result.get(i - 1).getPosition();
                if (result.get(i - 1).getCount().equals(statsItem.getCount())) {
                    statsItem.setPosition(beforePosition);
                } else {
                    statsItem.setPosition(beforePosition + 1);
                }
            } else {
                statsItem.setPosition(1);
            }
        }

        Collections.sort(result, nameComparator);
    }
}
