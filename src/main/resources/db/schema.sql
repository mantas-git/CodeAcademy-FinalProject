create table devices
(
    id  INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    vnr varchar(255) NOT NULL,
    comment varchar(255) NOT NULL,
    create_date DATE,
    user_id  INT
);