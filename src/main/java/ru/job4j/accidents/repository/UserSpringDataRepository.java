package ru.job4j.accidents.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.accidents.model.User;

public interface UserSpringDataRepository extends CrudRepository<User, Integer> {

}
