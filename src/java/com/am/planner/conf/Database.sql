/**
 * Author:  Marques
 * Created: 14/08/2017
 */

create database planner_db;

use planner_db;

create table users
(
    id_user     int             not null auto_increment,
    name        varchar(160)    not null,
    login       varchar(160)    not null,
    password    varchar(64)     not null,
    state       varchar(1)      not null,
    profile     int             not null,
    
    constraint pk_users primary key (id_user)
);

create table organizations
(
    id_organization     int             not null auto_increment,
    name                varchar(160)    not null,
    description         text            not null,
    date_register       date            not null,
    state               varchar(1)      not null,
    address             varchar(160)    not null,
    city                varchar(160)    not null,
    uf                  int             not null,

    constraint pk_organization primary key (id_organization)
);

create table planners
(
    id_planner      int             not null auto_increment,
    name            varchar(160)    not null,
    description     text            not null,
    date_register   date,
    state           varchar(1)      not null,
    id_user         int             not null,
    id_organization int             not null,
    progress        int             not null,
    
    constraint pk_planners      primary key (id_planner),
    constraint fk_users         foreign key (id_user) references users(id_user),
    constraint fk_organization  foreign key (id_organization) references organizations(id_organization)
);

create table tasks
(
    id_task         int             not null auto_increment,
    name            varchar(160)    not null,
    description     text,
    date_start      date            not null,
    date_finished   date,
    state           varchar(1)      not null,
    progress        int             not null,
    id_planner      int             not null,
    
    constraint pk_tasks     primary key (id_task),
    constraint fk_planners  foreign key (id_planner) references planners(id_planner)
);

create table sessions
(
    id_session  int             not null auto_increment,
    date_login  timestamp       default current_timestamp(),
    id_user     int             not null,
    
    constraint pk_sessions primary key (id_session),
    constraint fk_ref_user foreign key (id_user) references users(id_user)
);