package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.RuleStore;

import java.util.Collection;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleRuleService implements RuleService {
    private final RuleStore ruleMem;

    @Override
    public Collection<Rule> findAll() {
        return ruleMem.findAll();
    }

    @Override
    public Optional<Rule> findById(int id) {
        return ruleMem.findById(id);
    }
}
