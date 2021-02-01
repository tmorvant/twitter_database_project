-- Q3 Optimization: Was 8388608 (8MB)  Now 1073741824 (1GB)
SET GLOBAL innodb_buffer_pool_size=1073741824;
-- Q3 UNDO COMMAND
SET GLOBAL innodb_buffer_pool_size=8388608;

-- Q7
ALTER TABLE tweet
ADD COLUMN posted_month int;
ALTER TABLE tweet
ADD COLUMN posted_year int;
UPDATE tweet
SET posted_month = month(tweet.posted);
UPDATE tweet
SET posted_year = year(tweet.posted);
-- Q7 UNDO COMMAND
ALTER TABLE tweet
DROP COLUMN posted_month;
ALTER TABLE tweet
DROP COLUMN posted_year;

-- Q9
CREATE INDEX q9_index
ON twitter_user (subcategory);
-- Q9 UNDO COMMAND
DROP INDEX q9_index
ON twitter_user; 

-- Q16
ALTER TABLE tweet
ADD COLUMN posted_month int;
ALTER TABLE tweet
ADD COLUMN posted_year int;
UPDATE tweet
SET posted_month = month(tweet.posted);
UPDATE tweet
SET posted_year = year(tweet.posted);
-- Q16 UNDO COMMAND
ALTER TABLE tweet
DROP COLUMN posted_month;
ALTER TABLE tweet
DROP COLUMN posted_year;

-- Q18 Optimization: Was 8388608 (8MB)  Now 1073741824 (1GB)
SET GLOBAL innodb_buffer_pool_size=1073741824;
SET GLOBAL innodb_buffer_pool_size=8388608; -- Undo command

-- Q23
CREATE INDEX q23_index_user
ON twitter_user (subcategory);
-- Undo
DROP INDEX q23_index_user
ON twitter_user;