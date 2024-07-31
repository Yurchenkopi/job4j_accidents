package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.mapper.AccidentRowMapper;

import java.util.Collection;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class AccidentJdbc implements AccidentStore {
    private final JdbcTemplate jdbc;

    private final AccidentRowMapper accidentRowMapper;

    @Override
    public Collection<Accident> findAll() {
        return jdbc.query(
                """
                        SELECT
                        a.id AS a_id,
                        a.name AS a_name,
                        at.id AS at_id, at.name AS at_name,
                        r.id AS r_id, r.name AS r_name,
                        a.text AS a_text,
                        a.address AS a_address
                        FROM accidents a
                        JOIN accident_types at ON a.type_id = at.id
                        LEFT JOIN accidents_rules ar ON a.id = ar.accident_id
                        JOIN rules r ON ar.rule_id = r.id
                        """,
                accidentRowMapper);
    }

    @Override
    public Accident create(Accident accident) {
        jdbc.update("insert into accidents (name, type_id, text, address) values (?, ?, ?, ?)",
                accident.getName(),
                accident.getType().getId(),
                accident.getText(),
                accident.getAddress());
        for (Rule r : accident.getRules()) {
            jdbc.update("insert into accidents_rules (accident_id, rule_id) values (?, ?)",
                    accident.getId(),
                    r.getId());
        }
        return accident;
    }

    @Override
    public boolean update(Accident accident) {
        String sqlAccidentsUpdate = """
                UPDATE accidents
                SET name = ?, type_id = ?, text = ?, address = ?
                WHERE id = ?
                """;
        String sqlAccidentsRulesDelete = """
                DELETE FROM accidents_rules
                WHERE accident_id = ?
                """;
        String sqlAccidentsRulesInsert = """
                INSERT INTO accidents_rules (accident_id, rule_id)
                VALUES (?, ?)
                """;
        boolean rsl1 = jdbc.update(sqlAccidentsUpdate,
                accident.getName(),
                accident.getType().getId(),
                accident.getText(),
                accident.getAddress()) != 0;
        boolean rsl2 = jdbc.update(sqlAccidentsRulesDelete, accident.getId()) != 0;
        for (Rule r : accident.getRules()) {
            jdbc.update(sqlAccidentsRulesInsert,
                    accident.getId(),
                    r.getId());
        }
        return rsl1 || rsl2;
    }

    @Override
    public Optional<Accident> findById(Integer accidentId) {
        String sql = """
                        SELECT
                        a.id AS a_id,
                        a.name AS a_name,
                        at.id AS at_id, at.name AS at_name,
                        r.id AS r_id, r.name AS r_name,
                        a.text AS a_text,
                        a.address AS a_address
                        FROM accidents a
                        JOIN accident_types at ON a.type_id = at.id
                        LEFT JOIN accidents_rules ar ON a.id = ar.accident_id
                        JOIN rules r ON ar.rule_id = r.id
                        WHERE a.id = ?
                        """;

        return Optional.ofNullable(jdbc.queryForObject(
                sql, new Object[]{accidentId},
                accidentRowMapper));
    }
}
