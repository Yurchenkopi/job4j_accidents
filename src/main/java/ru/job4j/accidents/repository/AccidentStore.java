package ru.job4j.accidents.repository;

import ru.job4j.accidents.model.Accident;

import java.util.Collection;
import java.util.Optional;

public interface AccidentStore {
    Collection<Accident> findAll();

    Accident create(Accident accident);

    boolean update(Accident accident);

    Optional<Accident> findById(Integer accidentId);
}
