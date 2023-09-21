alter table users
    add column verification_code varchar(64);

alter table users
    add column enabled boolean not null default false;