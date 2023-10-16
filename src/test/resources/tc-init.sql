--TODO sizes need verification
create table PRODUCTS
(
    id           char(25) primary key,
    name         varchar(256) not null,
    quantity     int,
    category     varchar(128) not null,
    sub_category varchar(128) not null
);

create table ORDERS
(
    id            SERIAL primary key,
    order_id      char(36)       not null,
    product_id    char(25)       not null,
    currency      char(3)        not null,
    quantity      int            not null,
    shipping_cost NUMERIC(15, 3) not null,
    amount        NUMERIC(15, 3) not null,
    channel       varchar(25)    not null,
    channel_group varchar(25)    not null,
    campaign      varchar(255),
    timestamp     timestamp(2)   not null,
    CONSTRAINT fk_product FOREIGN KEY (product_id) REFERENCES products (id)
);

insert into products (id, name, quantity, category, sub_category) values ('prod1548#prod104001000080','High-top sneakers',8,'Shoes','Sneakers');
insert into orders (order_id,product_id,currency,quantity,shipping_cost,amount,channel,channel_group,campaign,timestamp) values ('ef391c30-fc8d-31d9-aa13-530b6e165591','prod1548#prod104001000080', 'SEK',1,0,12615.063,'direct','direct', NULL, '2023-03-10T12:57:52Z');
