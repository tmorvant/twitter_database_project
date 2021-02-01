-- Q3 k = 5 and year = 2016
SELECT COUNT(DISTINCT tUser.state) AS statenum, group_concat(DISTINCT tUser.state ORDER BY tUser.state) as States, tweet_tag.tag_name as name
FROM tweet_tag
JOIN tweet ON tweet.tid = tweet_tag.tid
JOIN twitter_user AS tUser ON tUser.screen_name = tweet.posting_user
WHERE tUser.state != "" and tUser.state != "na" and year(tweet.posted) = '2016'
GROUP BY tweet_tag.tag_name
ORDER BY statenum DESC
LIMIT 5;

-- Q9 sub-category = 'GOP' k = 5
SELECT twitter_user.screen_name, twitter_user.subcategory, twitter_user.numFollowers
FROM twitter_user
WHERE twitter_user.subcategory = 'GOP'
ORDER BY twitter_user.numFollowers DESC
LIMIT 5;

-- Q18 k = 5 sub-category = 'GOP', month = 1, year = 2016
SELECT mentioned.screen_name as mentionedUser, mentioned.state as mentionedUserState, group_concat(DISTINCT mentionee.screen_name ORDER BY mentionee.screen_name) as postingUsers
FROM twitter_user mentioned
JOIN mention AS men ON men.screen_name = mentioned.screen_name
JOIN tweet ON tweet.tid = men.tid AND month(tweet.posted) = 1 AND year(tweet.posted) = 2016
JOIN twitter_user AS mentionee ON mentionee.screen_name = tweet.posting_user
WHERE mentionee.subcategory = 'GOP'
GROUP BY mentioned.screen_name
ORDER BY COUNT(men.tid) DESC
LIMIT 5;

-- Insert: For this one you will need to replace all the values with variables
INSERT INTO twitter_user (screen_name, name, numFollowers, category, subcategory, state)
VALUES ('screen_name', 'name', 0, 'category', 'sub', 'state')


