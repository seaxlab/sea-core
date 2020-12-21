Create TABLE `user`
(
    `id`      int(11) NOT NULL AUTO_INCREMENT,
    `name`    varchar(50)  DEFAULT NULL,
    `age`     int(11)      DEFAULT NULL,
    `address` varchar(200) DEFAULT NULL,
    PRIMARY KEY (`id`)
);

insert into user(name) value ('abc');