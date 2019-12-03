create table users (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    username varchar(100) unique not null
);