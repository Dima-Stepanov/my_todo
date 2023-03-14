--Создание таблицы с задачами
CREATE TABLE tasks
(
    id          serial primary key,
    description TEXT,
    created     TIMESTAMP,
    done        BOOLEAN
);