
show variables like 'secure_file_priv';

use group89;
LOAD DATA INFILE 'C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/2016/user.csv' REPLACE INTO TABLE twitter_user
FIELDS TERMINATED BY ';' OPTIONALLY ENCLOSED BY '"' LINES TERMINATED BY '\n'
IGNORE 1 LINES
(screen_name,`name`,subcategory,category,state,numFollowers, numFollowing);

use group89;
LOAD DATA INFILE 'C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/2016/tweets.csv' REPLACE INTO TABLE tweet
FIELDS TERMINATED BY ';' OPTIONALLY ENCLOSED BY '"' LINES TERMINATED BY '\n'
IGNORE 1 LINES
(tid,textbody,retweet_count,@col4, posted, posting_user);

use group89;
LOAD DATA INFILE 'C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/2016/mentioned.csv' REPLACE INTO TABLE mention
FIELDS TERMINATED BY ';' OPTIONALLY ENCLOSED BY '"' LINES TERMINATED BY '\r\n'
IGNORE 1 LINES
(tid,screen_name);

use group89;
LOAD DATA INFILE 'C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/2016/tagged.csv' REPLACE INTO TABLE tweet_tag
FIELDS TERMINATED BY ';' OPTIONALLY ENCLOSED BY '"' LINES TERMINATED BY '\r\n'
IGNORE 1 LINES
(tid,tag_name);

use group89;
LOAD DATA INFILE 'C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/2016/urlused.csv' REPLACE INTO TABLE tweet_url
FIELDS TERMINATED BY ';' OPTIONALLY ENCLOSED BY '"' LINES TERMINATED BY '\r\n'
IGNORE 1 LINES
(tid,address);

