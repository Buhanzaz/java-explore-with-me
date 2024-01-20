create table if not exists categories
(
    id   bigint generated always as identity primary key ,
    name varchar(50) not null

);

create table if not exists compilations
(
    id     bigint generated always as identity primary key,
    pinned boolean,
    title  varchar(255)

);

create table if not exists compilations_events
(
    compilation_id bigint not null,
    event_id       bigint not null
);

create table if not exists events
(
    id                 bigint generated always as identity primary key,
    annotation         varchar(2000),
    confirmed_requests bigint,
    created_on         timestamp,
    description        varchar(7000),
    event_date         timestamp,
    paid               boolean,
    participant_limit  int,
    published_on       timestamp,
    request_moderation boolean,
    state              varchar(255),
    title              varchar(255),
    views              bigint,
    category_id        bigint not null,
    initiator_id       bigint not null,
    location_id        bigint not null
);

create table if not exists location
(
    id  bigint generated always as identity primary key,
    lat float8,
    lon float8
);

create table if not exists participation_request
(
    id           bigint generated always as identity primary key,
    created      timestamp    not null,
    status       varchar(255) not null,
    event_id     bigint       not null,
    requester_id bigint       not null
);

create table if not exists users
(
    id    bigint generated always as identity primary key,
    email varchar(254) not null,
    name  varchar(250) not null
);

alter table if exists categories
    add constraint UK_NAME unique (name);

alter table if exists participation_request
    add constraint UniqueEventAndRequester unique (event_id, requester_id);

alter table if exists users
    add constraint UK_EMAIL unique (email);

alter table if exists compilations_events
    add constraint FK_EVENT_ID_FOR_COMPILATIONS_EVENTS
        foreign key (event_id)
            references events;

alter table if exists compilations_events
    add constraint FK_COMPILATION_ID
        foreign key (compilation_id)
            references compilations;

alter table if exists events
    add constraint FK_CATEGORY_ID
        foreign key (category_id)
            references categories;

alter table if exists events
    add constraint FK_INITIATOR_ID
        foreign key (initiator_id)
            references users;

alter table if exists events
    add constraint FK_LOCATION_ID
        foreign key (location_id)
            references location;

alter table if exists participation_request
    add constraint FK_EVENT_ID_FOR_PARTICIPATION_REQUEST
        foreign key (event_id)
            references events;

alter table if exists participation_request
    add constraint FK_REQUESTER_ID
        foreign key (requester_id)
            references users;