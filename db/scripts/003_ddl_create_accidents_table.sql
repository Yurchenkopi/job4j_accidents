CREATE TABLE accidents (
                           id serial primary key,
                           name text,
                           type_id int references accident_types(id),
                           text text,
                           address varchar
);