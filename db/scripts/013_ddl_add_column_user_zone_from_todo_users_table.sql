--Добавление колонки user_zone зоны часового пояса пользователя в таблицу todo_user.todo_users
ALTER  TABLE todo_users ADD COLUMN user_zone VARCHAR(30);