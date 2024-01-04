drop table if exists users cascade;

create table if not exists users
(
    id bigint generated always as identity primary key,
    email varchar(254) not null,
    name varchar(250) not null,
    CONSTRAINT UNIQUE_EMAIL UNIQUE (email)
)