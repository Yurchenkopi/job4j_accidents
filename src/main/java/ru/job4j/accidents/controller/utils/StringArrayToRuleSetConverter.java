package ru.job4j.accidents.controller.utils;

import lombok.AllArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.service.RuleService;

import java.util.HashSet;
import java.util.Set;

@Component
@AllArgsConstructor
public class StringArrayToRuleSetConverter implements Converter<String[], Set<Rule>> {
    private final RuleService simpleRuleService;

    @Override
    public Set<Rule> convert(String[] source) {
        Set<Rule> rsl = new HashSet<>();
        for (String id : source) {
            var ruleOptional = simpleRuleService.findById(Integer.parseInt(id));
            ruleOptional.ifPresent(rsl::add);
        }
        return rsl;
    }
}
