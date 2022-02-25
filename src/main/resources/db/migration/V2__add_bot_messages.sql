create table testbot.bot_messages
(
    id serial primary key,
    text varchar
);

insert into testbot.bot_messages (text)
values
    ('Привет, я HealtBot! Чтобы использовать мой функционал, необходимо ввести свои персональный данные. Напиши мне /register чтобы приступить к заполнению анкеты')