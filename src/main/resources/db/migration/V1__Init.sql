create table testbot.user_account
(
    id serial primary key,
    chat_id bigint,
    surname varchar,
    name varchar,
    patronymic varchar,
    active boolean,
    step varchar
);