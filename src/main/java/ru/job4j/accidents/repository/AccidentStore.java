package ru.job4j.accidents.repository;

import ru.job4j.accidents.model.Accident;

import java.util.Collection;

public interface AccidentStore {
    Collection<Accident> findAll();

    Accident save(Accident accident);
}
