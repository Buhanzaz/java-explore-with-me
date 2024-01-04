drop table if exists statistics cascade;

create table if not exists statistics
(
    id bigint generated always as identity primary key,
    app varchar(255) not null,
    uri varchar(255) not null,
    ipv4 varchar(20) not null,
    date_of_creation timestamp without time zone not null
);