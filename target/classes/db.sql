CREATE TABLE `testdb`.`event` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(45) NOT NULL,
  `date` DATE NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


CREATE TABLE `testdb`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


CREATE TABLE `testdb`.`ticket` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `event_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  `category` VARCHAR(45) NOT NULL,
  `place` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_ticket_event_id_idx` (`event_id` ASC) VISIBLE,
  INDEX `fk_ticket_user_id_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_ticket_event_id`
    FOREIGN KEY (`event_id`)
    REFERENCES `testdb`.`event` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ticket_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `testdb`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;



CREATE TABLE `testdb`.`user_account` (
  `user_id` INT NOT NULL,
  `money` INT NOT NULL,
  PRIMARY KEY (`user_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;
