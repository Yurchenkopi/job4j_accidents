package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.mapper.AccidentTypeRowMapper;

import java.util.Collection;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class AccidentTypeJdbc implements AccidentTypeStore {
    private final JdbcTemplate jdbc;

    private final AccidentTypeRowMapper accidentTypeRowMapper;

    @Override
    public Collection<AccidentType> findAll() {
        return jdbc.query(
                """
                        SELECT
                        at.id AS at_id, at.name AS at_name
                        FROM accident_types at
                        """,
                accidentTypeRowMapper);
    }

    @Override
    public Optional<AccidentType> findById(int id) {
        String sql = """
                        SELECT
                        at.id AS at_id, at.name AS at_name
                        FROM accident_types at
                        WHERE at.id = ?
                        """;

        return Optional.ofNullable(jdbc.queryForObject(
                sql, new Object[]{id},
                accidentTypeRowMapper));
    }
}
