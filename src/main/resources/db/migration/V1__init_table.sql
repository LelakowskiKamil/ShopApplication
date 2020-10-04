drop table if exists PRODUCTS;
create table PRODUCTS
(
    id          int primary key auto_increment,
    name varchar(25) not null,
    description varchar(100) not null,
    price        int
);
drop table if exists CUSTOMERS;
create table CUSTOMERS
(
    id          int primary key auto_increment,
    firstname varchar(100) not null,
    lastname  varchar(100) not null
);

drop table if exists SHOPPING_CARTS;
create table SHOPPING_CARTS
(
    id          int primary key auto_increment,
    cartorder_id          int not null ,
    foreign key (cartorder_id) references PRODUCTS (id)
);
