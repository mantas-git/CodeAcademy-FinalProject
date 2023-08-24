create table devices
(
    id           INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    device_id    INT UNIQUE ,
    transport_nr varchar(255) NOT NULL,
    comment      varchar(255) NOT NULL,
    create_date  DATE,
    user_id      INT
);

create table positions
(
    id        INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    device_id INT,
    date      varchar(255)     NOT NULL,
    latitude  DOUBLE PRECISION NOT NULL,
    longitude DOUBLE PRECISION NOT NULL,
    speed     INT
);

create table users
(
    id       INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    login    varchar(255) NOT NULL,
    password varchar(255) NOT NULL,
    email    varchar(255) NOT NULL
);