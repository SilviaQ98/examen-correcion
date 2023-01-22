create table public.books
(
    id     serial,
    isbn   varchar(16),
    title  varchar(128),
    author varchar(64),
    price  numeric(6, 2)
);

alter table public.books
    owner to postgres;