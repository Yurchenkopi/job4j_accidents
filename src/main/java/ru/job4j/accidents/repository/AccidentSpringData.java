package ru.job4j.accidents.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import ru.job4j.accidents.model.Accident;

import java.util.Collection;

public interface AccidentSpringData extends CrudRepository<Accident, Integer> {
    Collection<Accident> findAll(Sort sort);

}
