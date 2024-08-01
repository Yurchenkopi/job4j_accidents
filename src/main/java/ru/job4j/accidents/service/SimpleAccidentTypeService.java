package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.AccidentTypeStore;

import java.util.Collection;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleAccidentTypeService implements AccidentTypeService {
    private final AccidentTypeStore accidentTypeJdbc;

    @Override
    public Collection<AccidentType> findAll() {
        return accidentTypeJdbc.findAll();
    }

    @Override
    public Optional<AccidentType> findById(int id) {
        return accidentTypeJdbc.findById(id);
    }
}
