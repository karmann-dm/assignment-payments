create table if not exists users (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    username varchar(100) unique not null
);

create table if not exists currencies (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    code varchar(30) UNIQUE NOT NULL,
    full_name varchar(100) NOT NULL,
    minor_units integer not null
);

create table if not exists accounts (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    number varchar(20) unique not null ,
    user_id integer references users(id) on delete cascade on update cascade,
    currency_id integer references currencies(id) on delete cascade on update cascade,
    balance integer not null
);

create table if not exists currency_prices (
    cur_from integer references currencies(id) on delete cascade on update cascade ,
    cur_to integer references currencies(id) on delete cascade on update cascade ,
    price integer not null,
    primary key (cur_from, cur_to)
);

create table if not exists payments (
    id integer generated always as identity primary key,
    pid varchar(20) unique not null,
    account_from varchar(20) references accounts(number) on delete cascade on update cascade ,
    account_to varchar(20) references accounts(number) on delete cascade on update cascade ,
    price integer not null,
    status integer not null
);