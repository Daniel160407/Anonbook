ALTER TABLE `anonbook`.`post_comment`
    ADD COLUMN `id` INT NOT NULL AUTO_INCREMENT AFTER `comment_id`,
ADD PRIMARY KEY (`id`);
;
