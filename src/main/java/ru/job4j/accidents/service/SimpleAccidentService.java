package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.AccidentStore;

import java.util.Collection;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleAccidentService implements AccidentService {
    private final AccidentStore accidentHbm;

    @Override
    public Collection<Accident> findAll() {
        return accidentHbm.findAll();
    }

    @Override
    public Accident create(Accident accident) {
        return accidentHbm.create(accident);
    }

    @Override
    public boolean update(Accident accident) {
        return accidentHbm.update(accident);
    }

    @Override
    public Optional<Accident> findById(Integer accidentId) {
        return accidentHbm.findById(accidentId);
    }
}
