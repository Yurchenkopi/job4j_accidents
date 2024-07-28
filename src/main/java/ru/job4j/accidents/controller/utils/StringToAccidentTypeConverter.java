package ru.job4j.accidents.controller.utils;

import lombok.AllArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.service.AccidentTypeService;

import java.util.Optional;

@Component
@AllArgsConstructor
public class StringToAccidentTypeConverter implements Converter<String, AccidentType> {
    private final AccidentTypeService simpleAccidentTypeService;

    @Override
    public AccidentType convert(String source) {
        AccidentType rsl = null;
        Optional<AccidentType> typeOptional = simpleAccidentTypeService.findById(Integer.parseInt(source));
        if (typeOptional.isPresent()) {
            rsl = typeOptional.get();
        }
        return rsl;
    }
}
