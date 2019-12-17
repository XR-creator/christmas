package ru.popov.christmas.service.dao;

import org.springframework.data.repository.CrudRepository;
import ru.popov.christmas.model.User;

public interface UserRepository extends CrudRepository<User, Long> {

    User findByName(String name);

    User findByEmail(String email);
}
