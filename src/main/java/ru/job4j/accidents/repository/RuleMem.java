package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Repository
public class RuleMem implements RuleStore {
    private final Collection<Rule> rules = new ArrayList<>() {
        {
            add(new Rule(1, "Статья 1"));
            add(new Rule(2, "Статья 2"));
            add(new Rule(3, "Статья 3"));
            add(new Rule(4, "Статья 4"));
        }
    };

    @Override
    public Collection<Rule> findAll() {
        return rules;
    }

    @Override
    public Optional<Rule> findById(int id) {
        return rules.stream()
                .filter(r -> r.getId() == id)
                .findFirst();
    }
}
