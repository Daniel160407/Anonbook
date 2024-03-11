ALTER TABLE `anonbook`.`images`
ADD COLUMN `posttext` VARCHAR(45) NULL AFTER `imgname`,
CHANGE COLUMN `name` `imgname` VARCHAR(45) NULL , RENAME TO  `anonbook`.`post` ;