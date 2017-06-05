DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `from_id` INT NULL,
  `to_id` INT NULL,
  `content` TEXT NULL,
  `created_date` DATETIME NULL,
  `has_read` INT NULL,
  `conversation_id` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `conversation_index` (`conversation_id` ASC),
  INDEX `created_date` (`created_date` ASC))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;