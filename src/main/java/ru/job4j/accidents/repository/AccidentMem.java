package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AccidentMem implements AccidentStore {
    private final AtomicInteger nextId = new AtomicInteger(1);
    private final Map<Integer, Accident> accidents = new ConcurrentHashMap<>();

    public AccidentMem() {
        init();
    }

    private void init() {
        var defaultAccidentType = new AccidentType(4, "Неизвестно");
        var defaultRules = Set.of(new Rule(1, "Статья 1"));
        create(new Accident(0, "Тестовое название 1", defaultAccidentType, defaultRules, "Тестовый текст 1", "Адрес 1"));
        create(new Accident(0, "Тестовое название 2", defaultAccidentType, defaultRules, "Тестовый текст 2", "Адрес 2"));
        create(new Accident(0, "Тестовое название 3", defaultAccidentType, defaultRules, "Тестовый текст 3", "Адрес 3"));
        create(new Accident(0, "Тестовое название 4", defaultAccidentType, defaultRules, "Тестовый текст 4", "Адрес 4"));
        create(new Accident(0, "Тестовое название 5", defaultAccidentType, defaultRules, "Тестовый текст 5", "Адрес 5"));
    }

    @Override
    public Collection<Accident> findAll() {
        return accidents.values();
    }

    @Override
    public Accident create(Accident accident) {
        accident.setId(nextId.getAndIncrement());
        accidents.put(accident.getId(), accident);
        return accidents.get(accident.getId());
    }

    @Override
    public boolean update(Accident accident) {
        return accidents.computeIfPresent(accident.getId(),
                (id, oldAccident) -> new Accident(
                        oldAccident.getId(), accident.getName(),
                        accident.getType(), accident.getRules(),
                        accident.getText(), accident.getAddress()
                )) != null;
    }

    @Override
    public Optional<Accident> findById(Integer accidentId) {
        return Optional.ofNullable(accidents.get(accidentId));
    }
}
