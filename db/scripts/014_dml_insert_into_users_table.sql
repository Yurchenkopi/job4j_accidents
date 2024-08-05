insert into users (username, enabled, password, authority_id)
values ('root', true, '$2a$10$hW/9w33TA1mmGkmMbs8df.G65zzs6S15bCcVgCHZZCsEQLxtkjxk6',
        (select id from authorities where authority = 'ROLE_ADMIN'));