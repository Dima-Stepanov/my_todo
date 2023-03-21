--Обновление таблицы tasks заполение поля priority_id
UPDATE tasks SET priority_id = (SELECT ID FROM priorities WHERE name = 'urgently') WHERE id <=3;
UPDATE tasks SET priority_id = (SELECT ID FROM priorities WHERE name = 'normal') WHERE id > 3 AND id <= 6;