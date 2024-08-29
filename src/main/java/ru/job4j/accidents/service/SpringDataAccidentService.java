package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.AccidentSpringData;

import java.util.Collection;
import java.util.Optional;

@Service

@AllArgsConstructor
public class SpringDataAccidentService implements AccidentService {
    private final AccidentSpringData accidentSpringData;

    private static final Logger LOG = LoggerFactory.getLogger(SpringDataAccidentService.class.getName());


    @Override
    public Collection<Accident> findAll() {
        return accidentSpringData.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @Override
    public Accident create(Accident accident) {
        return accidentSpringData.save(accident);
    }

    @Override
    public boolean update(Accident accident) {
        boolean  rsl = false;
        Optional<Accident> currentAccident = accidentSpringData.findById(accident.getId());
        if (currentAccident.isPresent()) {
            if (!currentAccident.get().equals(accident)) {
                try {
                    accidentSpringData.save(accident);
                    rsl = true;
                } catch (Exception e) {
                    LOG.error("Возникло исключение при обновлении записей в БД.");
                }
            }
        }
        return rsl;
    }

    @Override
    public Optional<Accident> findById(Integer accidentId) {
        return accidentSpringData.findById(accidentId);
    }
}