-- create schema to be used with example-load-in-file.
-- 
DROP DATABASE IF EXISTS `group89`;
CREATE DATABASE `group89`;
USE `group89`;

DROP TABLE IF EXISTS `twitter_user`;
CREATE TABLE `twitter_user` (
	`screen_name` varchar(60) UNIQUE NOT NULL,
    `name` varchar(60),
    `numFollowers` int DEFAULT NULL,
    `numFollowing` int DEFAULT NULL,
    `category` varchar(25) DEFAULT NULL,
    `subcategory` varchar(9) DEFAULT NULL,
    `state` varchar(20) DEFAULT "na",
    PRIMARY KEY(`screen_name`)
); 

DROP TABLE IF EXISTS `tweet`;
CREATE TABLE `tweet` (
  `tid` bigint NOT NULL,
  `posting_user` varchar(60) NOT NULL,
  `textbody` text,
  `retweet_count` int(11) DEFAULT NULL,
  `posted` datetime DEFAULT NULL,
  PRIMARY KEY (`tid`),
  FOREIGN KEY (`posting_user`) references twitter_user(`screen_name`) ON DELETE CASCADE);

DROP TABLE IF EXISTS `mention`;
CREATE TABLE `mention` (
	`tid` bigint NOT NULL,
	`screen_name` varchar(60) NOT NULL,
    PRIMARY KEY(`tid`, `screen_name`),
    FOREIGN KEY(`screen_name`) references twitter_user(`screen_name`)  ON DELETE CASCADE,
	FOREIGN KEY(`tid`) references tweet(`tid`)  ON DELETE CASCADE
); 

DROP TABLE IF EXISTS `tweet_tag`;
CREATE TABLE `tweet_tag` (
	`tag_name` varchar(60) NOT NULL,
	`tid` bigint NOT NULL,
    PRIMARY KEY(`tag_name`, `tid`),
    FOREIGN KEY(`tid`) references tweet(`tid`) ON DELETE CASCADE
); 

DROP TABLE IF EXISTS `tweet_url`;
CREATE TABLE `tweet_url` (
	`address` varchar(600) NOT NULL,
	`tid` bigint NOT NULL,
    PRIMARY KEY(`address`, `tid`),
    FOREIGN KEY(`tid`) references tweet(`tid`) ON DELETE CASCADE
); 