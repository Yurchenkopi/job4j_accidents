package ru.job4j.accidents.repository.mapper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.jdbc.core.RowMapper;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Map;

@Getter
@AllArgsConstructor
public class AccidentRowMapper implements RowMapper<Accident> {
    private final Map<Integer, Accident> accidentMap;

    @Override
    public Accident mapRow(ResultSet rs, int rowNum) throws SQLException {
        Accident accident = new Accident();
        Integer accidentId = rs.getInt("a_id");
        if (!accidentMap.containsKey(accidentId)) {
            accident.setId(accidentId);
            accident.setName(rs.getString("a_name"));
            accident.setText(rs.getString("a_text"));
            accident.setAddress(rs.getString("a_address"));

            AccidentType type = new AccidentType();
            type.setId(rs.getInt("at_id"));
            type.setName(rs.getString("at_name"));
            accident.setType(type);

            accidentMap.put(accidentId, accident);
        }

        Rule rule = new Rule();
        rule.setId(rs.getInt("r_id"));
        rule.setName(rs.getString("r_name"));
        accident = accidentMap.get(accidentId);
        if (accident.getRules() == null) {
            accident.setRules(new HashSet<>());
        }
        accident.getRules().add(rule);

        return accidentMap.get(accidentId);
    }
}