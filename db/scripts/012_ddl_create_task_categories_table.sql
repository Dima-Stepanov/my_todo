--Таблица для связи @ManyToMany между таблицами tasks и categories
create table tasks_categories
(
    id          serial primary key,
    task_id     int not null references tasks (id),
    category_id int not null references categories (id),
    UNIQUE (task_id, category_id)
);