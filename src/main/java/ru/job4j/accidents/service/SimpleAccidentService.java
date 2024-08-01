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
    private final AccidentStore accidentJdbc;

    @Override
    public Collection<Accident> findAll() {
        return accidentJdbc.findAll();
    }

    @Override
    public Accident create(Accident accident) {
        return accidentJdbc.create(accident);
    }

    @Override
    public boolean update(Accident accident) {
        return accidentJdbc.update(accident);
    }

    @Override
    public Optional<Accident> findById(Integer accidentId) {
        return accidentJdbc.findById(accidentId);
    }
}
