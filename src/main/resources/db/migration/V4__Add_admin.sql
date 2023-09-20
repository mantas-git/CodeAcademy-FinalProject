insert into users (username, password, active, email)
    values ('admin', '$2a$10$t8kFBTzcYC5fTFrC.x9Q7.5X6PPhkTjGJcxaZezivgIsIVg8og2eS', true, 'admin@admin.com'),
           ('user', '$2a$10$Ju6Uj48ahQ7VTWs6urvvAeF81FOKusd9zzQLyUZ9tZ0DEuXE.Za8q', true, 'user@user.com');

insert into user_role (user_id, roles)
    values (1, 'ADMIN'),
           (2, 'USER');
