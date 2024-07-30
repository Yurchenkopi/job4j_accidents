package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;

import java.util.Collection;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class AccidentJdbc implements AccidentStore {
    private final JdbcTemplate jdbc;

    @Override
    public Collection<Accident> findAll() {
        Collection<Accident> accidents = jdbc.query("SELECT a.id AS a_id, a.name AS a_name, a.text AS a_text, a.address AS a_address, " +
                        "at.id AS at_id, at.name AS at_name, r.id AS r_id, r.name AS r_name " +
                        "FROM accidents a " +
                        "JOIN accident_types at ON a.type_id = at.id " +
                        "LEFT JOIN accidents_rules ar ON a.id = ar.accident_id " +
                        "LEFT JOIN rules r ON ar.rule_id = r.id",
                (rs, rowNum) -> {
                    Accident accident = new Accident();
                    accident.setId(rs.getInt("a_id"));
                    accident.setName(rs.getString("a_name"));
                    accident.setText(rs.getString("a_text"));
                    accident.setAddress(rs.getString("a_address"));

                    AccidentType type = new AccidentType();
                    type.setId(rs.getInt("at_id"));
                    type.setName(rs.getString("at_name"));

                    accident.setType(type);

                    if (rs.getInt("r_id") != 0) {
                        Rule rule = new Rule();
                        rule.setId(rs.getInt("r_id"));
                        rule.setName(rs.getString("r_name"));
                        accident.getRules().add(rule);
                    }

                    return accident;
                });

        return accidents;
    }

    @Override
    public Accident create(Accident accident) {
        jdbc.update("insert into accidents (name, type_id, text, address) values (?, ?, ?, ?)",
                accident.getName(),
                accident.getType().getId(),
                accident.getText(),
                accident.getAddress());
        return accident;
    }

    @Override
    public boolean update(Accident accident) {
        return false;
    }

    @Override
    public Optional<Accident> findById(Integer accidentId) {
        return Optional.empty();
    }
}
