package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Repository
public class AccidentTypeMem implements AccidentTypeStore {
    private final Collection<AccidentType> accidentTypes = new ArrayList<>() {
        {
            add(new AccidentType(1, "Две машины"));
            add(new AccidentType(2, "Машина и человек"));
            add(new AccidentType(3, "Машина и велосипед"));
            add(new AccidentType(4, "Неизвестно"));
        }
    };
    @Override
    public Collection<AccidentType> findAll() {
        return accidentTypes;
    }

    @Override
    public Optional<AccidentType> findById(int id) {
        return accidentTypes.stream()
                .filter(t -> t.getId() == id)
                .findFirst();
    }
}
