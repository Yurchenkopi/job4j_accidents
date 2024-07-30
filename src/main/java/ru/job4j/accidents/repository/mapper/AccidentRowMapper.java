package ru.job4j.accidents.repository.mapper;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
@AllArgsConstructor
public class AccidentRowMapper implements RowMapper<Accident> {
    @Override
    public Accident mapRow(ResultSet rs, int rowNum) throws SQLException {
        Accident accident = new Accident();
        accident.setId(rs.getInt("a_id"));
        accident.setName(rs.getString("a_name"));
        accident.setText(rs.getString("a_text"));
        accident.setAddress(rs.getString("a_address"));

        AccidentType type = new AccidentType();
        type.setId(rs.getInt("at_id"));
        type.setName(rs.getString("at_name"));

        accident.setType(type);

        Rule rule = new Rule();
        rule.setId(rs.getInt("r_id"));
        rule.setName(rs.getString("r_name"));
        accident.getRules().add(rule);

        return accident;
    }
}