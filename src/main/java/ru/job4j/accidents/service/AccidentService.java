package ru.job4j.accidents.service;

import ru.job4j.accidents.model.Accident;

import java.util.Collection;
import java.util.Optional;

public interface AccidentService {
    Collection<Accident> findAll();

    Accident create(Accident accident);

    boolean update(Accident accident);

    Optional<Accident> findById(Integer accidentId);
}
