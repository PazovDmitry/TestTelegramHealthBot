create table testbot.bot_questions
(
    id serial primary key,
    text varchar,
    is_open_question boolean
);

insert into testbot.bot_questions (text, is_open_question)
values
    ('Как у тебя дела?', false ),
    ('Оцени свое самочувстие', false),
    ('Если у тебя есть жалобы на здоровье, опиши их', true),
    ('Что тебя беспокоит?', true);