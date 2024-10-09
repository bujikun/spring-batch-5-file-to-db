create table if not exists `sales_info`
(
    `id`        bigint auto_increment primary key,
    `product`   varchar(255) not null,
    `seller`    varchar(255) not null,
    `seller_id` int          not null,
    `price`     decimal      not null,
    `city`      varchar(255) not null,
    `category`  varchar(255) not null,
    `created_at` datetime default current_timestamp

);