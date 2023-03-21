--Обновление структуры таблицы tasks добавление столбца priority_id
alter table tasks add column priority_id int references priorities (id);