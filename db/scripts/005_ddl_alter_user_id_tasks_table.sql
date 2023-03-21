--Добавление в таблицу tasks связи Many-to-on с таблицей todo_users
ALTER TABLE TASKS
    ADD COLUMN USER_ID
        INT
        NOT NULL
        DEFAULT 1
        REFERENCES TODO_USERS(ID);