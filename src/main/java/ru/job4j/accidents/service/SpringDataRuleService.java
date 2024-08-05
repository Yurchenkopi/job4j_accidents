package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.RuleSpringData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SpringDataRuleService implements RuleService {
    private RuleSpringData ruleSpringData;

    @Override
    public Collection<Rule> findAll() {
        return ruleSpringData.findAll();
    }

    @Override
    public Optional<Rule> findById(int id) {
        return ruleSpringData.findById(id);
    }
}
