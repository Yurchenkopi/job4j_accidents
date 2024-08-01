package ru.job4j.accidents.repository.mapper;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.job4j.accidents.model.Rule;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
@AllArgsConstructor
public class RuleRowMapper implements RowMapper<Rule> {
    @Override
    public Rule mapRow(ResultSet rs, int rowNum) throws SQLException {
        Rule rule = new Rule();
        rule.setId(rs.getInt("r_id"));
        rule.setName(rs.getString("r_name"));

        return rule;
    }
}