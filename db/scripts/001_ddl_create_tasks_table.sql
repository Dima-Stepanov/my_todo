--Создание таблицы с задачами
CREATE TABLE tasks
(
    id          serial primary key,
    description TEXT,
    created     TIMESTAMP WITH TIME ZONE DEFAULT now(),
    done        BOOLEAN DEFAULT false
);