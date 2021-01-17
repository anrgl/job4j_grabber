create database job_aggregator;

\c job_aggregator; -- use database

create table job (
    id serial primary key,
    name varchar(255),
    text text,
    link varchar(255) unique,
    created date
);