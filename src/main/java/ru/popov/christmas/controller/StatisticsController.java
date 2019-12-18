package ru.popov.christmas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.popov.christmas.dto.GroupStatsDTO;
import ru.popov.christmas.dto.UserStatsDTO;
import ru.popov.christmas.service.StatisticsService;

import java.util.List;

@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {

    @Autowired
    private StatisticsService statisticsService;

    @GetMapping("/user")
    @ResponseBody
    public List<UserStatsDTO> getStatsByUser(OAuth2AuthenticationToken authenticationToken) {
        return statisticsService.getStatsByUser(authenticationToken);
    }

    @GetMapping("/group")
    @ResponseBody
    public List<GroupStatsDTO> getStatsByGroup(OAuth2AuthenticationToken authenticationToken) {
        return statisticsService.getStatsByGroup(authenticationToken);
    }
}
