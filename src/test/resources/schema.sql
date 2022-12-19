drop table if exists item CASCADE;
drop table if exists address CASCADE;
drop table if exists category CASCADE;
drop table if exists upload_file CASCADE;
drop table if exists item_image CASCADE;


create table address(
    zonecode integer,
    address varchar(30),
    primary key (zonecode)
);

create table category(
    id bigint generated by default as identity,
    kr varchar(20),
    en varchar(20),
    primary key (id)
);

create table upload_file(
     store_file_name varchar(50),
     upload_file_name varchar(50),
     primary key (store_file_name)
);


create table item(
    id bigint generated by default as identity,
    title varchar(20),
    zonecode integer NOT NULL,
    address_detail varchar(50),
    description varchar(100),
    category_id bigint,
    thumbnail varchar(50),
    created_at varchar(50),
    updated_at varchar(50),
    FOREIGN KEY(zonecode) REFERENCES ADDRESS(ZONECODE),
    FOREIGN KEY(category_id) REFERENCES CATEGORY(ID),
    primary key (id)
);

create table item_image(
    store_file_name varchar(50) NOT NULL,
    item_id bigint NOT NULL,
    primary key(store_file_name),
    FOREIGN KEY(store_file_name) REFERENCES upload_file(store_file_name),
    FOREIGN KEY(item_id) REFERENCES ITEM(id)
);

INSERT INTO CATEGORY (KR, EN) VALUES ('호텔', 'HOTEL');
INSERT INTO CATEGORY (KR, EN) VALUES ('나머지','ETC');
