-- -----------------------------------------------------
-- Players Table
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `wc_players` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `nick` VARCHAR(32) NOT NULL UNIQUE,
  `sum` INT NOT NULL DEFAULT 0,
  `streak` SMALLINT NOT NULL DEFAULT 0,
  `limit` INT NOT NULL DEFAULT 1024,
  `actual` INT NOT NULL DEFAULT 0,
  `last` DATETIME NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) 
  )
ENGINE = InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1;

-- -----------------------------------------------------
-- data_values Table
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `wc_transactions` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `player` INT NOT NULL,
  `block` INT DEFAULT NULL,
  `count` INT DEFAULT 0,
  `type` TINYINT DEFAULT 0,
  `date` DATETIME NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  CONSTRAINT FOREIGN KEY (`player`) REFERENCES `wc_players` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
  ) 
ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1;


-- -----------------------------------------------------
-- WC Shops signs
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `wc_signs` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `world` char(32) DEFAULT NULL,
  `x` INT NOT NULL,
  `y` INT NOT NULL,
  `z` INT NOT NULL,
  `direction` SMALLINT DEFAULT 0,
  `sells` INT DEFAULT 0,
  `buys` INT DEFAULT 0,
  PRIMARY KEY (`id`)
  ) 
ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1