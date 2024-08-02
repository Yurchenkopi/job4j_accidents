package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.mapper.AccidentRowMapper;

import java.sql.PreparedStatement;
import java.util.*;

@Repository
@AllArgsConstructor
public class AccidentJdbc implements AccidentStore {
    private final JdbcTemplate jdbc;

    @Override
    public Collection<Accident> findAll() {
        var accidentRowMapper = new AccidentRowMapper(new HashMap<>());
        jdbc.query(
                """
                        SELECT
                        a.id a_id,
                        a.name a_name,
                        at.id at_id, at.name at_name,
                        r.id r_id, r.name r_name,
                        a.text a_text,
                        a.address a_address
                        FROM accidents a
                        JOIN accident_types at ON a.type_id = at.id
                        LEFT JOIN accidents_rules ar on a.id = ar.accident_id
                        JOIN rules r on r.id = ar.rule_id
                        """,
                accidentRowMapper);
        return accidentRowMapper.getAccidentMap().values();
    }

    @Override
    public Accident create(Accident accident) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sqlInsertIntoAccidents = """
                INSERT INTO accidents (name, type_id, text, address)
                values (?, ?, ?, ?)
                """;
        String sqlInsertIntoAccidentsRules = """
                INSERT INTO accidents_rules (accident_id, rule_id)
                values (?, ?)
                """;

        jdbc.update(cn -> {
            PreparedStatement ps = cn.prepareStatement(
                    sqlInsertIntoAccidents,
                    new String[] {"id"});
            ps.setString(1, accident.getName());
            ps.setInt(2, accident.getType().getId());
            ps.setString(3, accident.getText());
            ps.setString(4, accident.getAddress());
            return ps;
        }, keyHolder);
        accident.setId(Objects.requireNonNull(keyHolder.getKey()).intValue());
        for (Rule r : accident.getRules()) {
            jdbc.update(
                    sqlInsertIntoAccidentsRules,
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
                accident.getAddress(),
                accident.getId()) != 0;
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
        var accidentRowMapper = new AccidentRowMapper(new HashMap<>());
        String sql = """
                        SELECT
                        a.id a_id,
                        a.name a_name,
                        at.id at_id, at.name at_name,
                        r.id r_id, r.name r_name,
                        a.text a_text,
                        a.address a_address
                        FROM accidents a
                        JOIN accident_types at ON a.type_id = at.id
                        LEFT JOIN accidents_rules ar on a.id = ar.accident_id
                        JOIN rules r on r.id = ar.rule_id
                        WHERE a.id = ?
                        """;
        jdbc.query(
                sql,
                accidentRowMapper,
                accidentId);
        return Optional.ofNullable(accidentRowMapper.getAccidentMap().values().iterator().next());
    }
}
