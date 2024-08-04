package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.AccidentTypeSpringData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SpringDataAccidentTypeService implements AccidentTypeService {
    private final AccidentTypeSpringData accidentTypeSpringData;

    @Override
    public Collection<AccidentType> findAll() {
        Collection<AccidentType> accidentTypes = new ArrayList<>();
        accidentTypeSpringData.findAll().forEach(accidentTypes::add);
        return accidentTypes;
    }

    @Override
    public Optional<AccidentType> findById(int id) {
        return accidentTypeSpringData.findById(id);
    }
}
