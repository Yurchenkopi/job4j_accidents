package ru.job4j.accidents.repository.mapper;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.job4j.accidents.model.AccidentType;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
@AllArgsConstructor
public class AccidentTypeRowMapper implements RowMapper<AccidentType> {
    @Override
    public AccidentType mapRow(ResultSet rs, int rowNum) throws SQLException {
        AccidentType accidentType = new AccidentType();
        accidentType.setId(rs.getInt("at_id"));
        accidentType.setName(rs.getString("at_name"));
        return accidentType;
    }
}