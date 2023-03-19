--Создание таблицы пользователей системы
create table todo_users
(
    id serial primary key,
    name varchar(30),
    email varchar(30) not null unique,
    password varchar(10) not null
);