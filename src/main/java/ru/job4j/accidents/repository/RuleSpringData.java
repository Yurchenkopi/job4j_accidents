package ru.job4j.accidents.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.accidents.model.Rule;

import java.util.Collection;

public interface RuleSpringData extends CrudRepository<Rule, Integer> {
    Collection<Rule> findAll();
}
