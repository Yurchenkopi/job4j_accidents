package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.AccidentStore;

import java.util.Collection;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleAccidentService implements AccidentService {
    private final AccidentStore accidentMap;

    @Override
    public Collection<Accident> findAll() {
        return accidentMap.findAll();
    }

    @Override
    public Accident create(Accident accident) {
        return accidentMap.create(accident);
    }

    @Override
    public boolean update(Accident accident) {
        return accidentMap.update(accident);
    }

    @Override
    public Optional<Accident> findById(Integer accidentId) {
        return accidentMap.findById(accidentId);
    }
}
