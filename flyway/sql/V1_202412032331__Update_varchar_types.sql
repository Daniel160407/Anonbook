ALTER TABLE `anonbook`.`post`
    CHANGE COLUMN `posttext` `posttext` VARCHAR(500) NULL DEFAULT NULL ,
    CHANGE COLUMN `imgname` `imgname` VARCHAR(500) NULL DEFAULT NULL ;
