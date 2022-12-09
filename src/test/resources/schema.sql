create table address(
    zonecode integer,
    address varchar(30),
    primary key (zonecode)
)

create table item(
id bigint generated by default as identity,
item_name varchar(20),
description varchar(50),
zonecode integer,
address_detail varchar(30),
created_at varchar(20),
last_update varchar(20),
primary key (id),
foreign key (zonecode) references address(zonecode)
)