-- drop table user;
create table user
(
    id          int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name        varchar(50)  DEFAULT NULL,
    age         int(11)      DEFAULT NULL,
    address     varchar(200) DEFAULT NULL,
    create_time datetime     default current_timestamp,
    update_time datetime     default current_timestamp,
    version     int(1)       default 1,
    is_deleted  tinyint(1)   default 0
);

insert into user(name)
values ('abc');