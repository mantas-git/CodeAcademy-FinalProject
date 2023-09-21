insert into users (username, password, email, active, verified)
values ('test', '$2a$10$Ju6Uj48ahQ7VTWs6urvvAeF81FOKusd9zzQLyUZ9tZ0DEuXE.Za8q',  'test@test.com', true, false);

insert into user_role (user_id, roles)
values (3, 'USER');