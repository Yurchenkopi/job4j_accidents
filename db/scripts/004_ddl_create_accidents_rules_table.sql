CREATE TABLE accidents_rules (
                       id serial primary key,
                       accident_id int references accidents(id),
                       rule_id int references  rules(id)
);