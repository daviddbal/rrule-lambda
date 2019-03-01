CREATE SCHEMA `rrule` ;
CREATE TABLE `rrule`.`history` (
  `uid` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `rrule_content` VARCHAR(80) NOT NULL,
  `dtstart_content` VARCHAR(45) NOT NULL,
  `max_recurrences` INT UNSIGNED NOT NULL,
  `created` DATETIME NOT NULL,
  `ip_address` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`uid`));
CREATE USER 'rruleuser'@'%' IDENTIFIED BY 'rrulepassword';
GRANT INSERT, SELECT, DELETE ON rrule.history TO 'rruleuser'@'%';