package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.mapper.RuleRowMapper;

import java.util.Collection;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class RuleJdbc implements RuleStore {
    private final JdbcTemplate jdbc;

    private final RuleRowMapper ruleTypeRowMapper;

    @Override
    public Collection<Rule> findAll() {
        return jdbc.query(
                """
                        SELECT
                        r.id AS r_id, r.name AS r_name
                        FROM rules r
                        """,
                ruleTypeRowMapper);
    }

    @Override
    public Optional<Rule> findById(int id) {
        String sql = """
                        SELECT
                        r.id AS r_id, r.name AS r_name
                        FROM rules r
                        WHERE r.id = ?
                        """;

        return Optional.ofNullable(jdbc.queryForObject(
                sql, new Object[]{id},
                ruleTypeRowMapper));
    }
}
