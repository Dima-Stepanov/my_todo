--Создание таблицы хранения категории задачи.
create table categories
(
    id   serial primary key,
    name varchar(30) not null unique
);