-- -----------------------------------------------------
-- Players Table
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `wc_players` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `uuid` char(36) NOT NULL UNIQUE,
  `nick` char(36) NOT NULL UNIQUE,
  `day` INT NOT NULL DEFAULT 0,
  `totalbuy` INT NOT NULL DEFAULT 0,
  `totalsell` INT NOT NULL DEFAULT 0,
  `tier` INT NOT NULL DEFAULT 0,
  `blocked` TINYINT(1) NOT NULL DEFAULT 0,
  `timestamp` DATETIME NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) 
  )
ENGINE = InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1;

-- -----------------------------------------------------
-- Transactions Table
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `wc_transactions` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `player` INT NOT NULL,
  `block` ENUM('oak','birch','spruce','jungle','dark','acacia'),
  `count` INT DEFAULT 0,
  `type` ENUM('sell','buy'),
  `price` FLOAT DEFAULT 0,
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
ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1;

-- -----------------------------------------------------
-- WC Version Info
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `wc_info` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `version` INT NOT NULL,
  PRIMARY KEY (`id`)
  ) 
ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1

INSERT INTO wc_info (version) VALUES (3)


