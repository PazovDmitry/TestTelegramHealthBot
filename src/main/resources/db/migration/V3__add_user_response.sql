create table testbot.user_responses
(
    id serial primary key,
    chat_id bigint,
    response varchar,
    question varchar,
    answer_date timestamptz,
    create_date timestamptz not null
);