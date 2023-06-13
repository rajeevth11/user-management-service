create database user_db;

CREATE TABLE `user_db`.`user` (
  `user_Id` INT NOT NULL AUTO_INCREMENT COMMENT 'User id of the User',
  `user_name` VARCHAR(100) NOT NULL COMMENT 'User Name of the user',
  `first_name` VARCHAR(100) NOT NULL COMMENT 'First name of the user',
  `last_name` VARCHAR(100) NOT NULL COMMENT 'Last name of the user',
  `email` VARCHAR(200) NOT NULL COMMENT 'Email of the user',
  `telephone_number` VARCHAR(15) NOT NULL COMMENT 'Telephone number of the user',
  PRIMARY KEY (`userId`),
  UNIQUE INDEX `userName_UNIQUE` (`userName` ASC) VISIBLE,
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE)
COMMENT = 'Persist data related to the User entity';