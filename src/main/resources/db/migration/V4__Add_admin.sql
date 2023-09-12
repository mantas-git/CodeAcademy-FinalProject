insert into users (username, password, active)
    values ('admin', '$2a$10$t8kFBTzcYC5fTFrC.x9Q7.5X6PPhkTjGJcxaZezivgIsIVg8og2eS', true);

insert into user_role (user_id, roles)
    values (1, 'ADMIN');
