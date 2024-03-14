CREATE TABLE `anonbook`.`comment`
(
    `id`      INT         NOT NULL AUTO_INCREMENT,
    `comment` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `anonbook`.`post_comment`
(
    `post_id`    INT NOT NULL AUTO_INCREMENT,
    `comment_id` INT NOT NULL,
    PRIMARY KEY (`post_id`)
);