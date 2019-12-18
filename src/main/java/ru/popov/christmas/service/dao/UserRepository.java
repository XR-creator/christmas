package ru.popov.christmas.service.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.popov.christmas.model.User;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {

    User findByName(String name);

    User findByEmail(String email);

    @Query(value = "SELECT user FROM User user join user.card card where card.cardType <> 'TEAM_LEAD'")
    List<User> findAllWithoutTeamLead();

    @Query(value = "SELECT user FROM User user join user.card card where card.cardType = 'TEAM_LEAD'")
    List<User> getAllTeamLeads();

    @Query(value = "SELECT sum(user.count) FROM User user join user.parent parent where parent.id = ?1")
    Integer getSumCountUsersByLead(Long leadId);
}
