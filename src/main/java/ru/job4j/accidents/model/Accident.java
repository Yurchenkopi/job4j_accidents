package ru.job4j.accidents.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "accidents")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Accident {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id")
    private AccidentType type;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "accidents_rules",
            joinColumns = {@JoinColumn(name = "accident_id", nullable = false, updatable = false)},
            inverseJoinColumns = {@JoinColumn(name = "rule_id", nullable = false, updatable = false)}
    )
    private Set<Rule> rules;

    private String text;

    private String address;
}
