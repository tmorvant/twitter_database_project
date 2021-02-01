-- Q3 k = 5 and year = 2016
SELECT COUNT(DISTINCT tUser.state) AS statenum, group_concat(DISTINCT tUser.state ORDER BY tUser.state) as States, tweet_tag.tag_name as name
FROM tweet_tag
JOIN tweet ON tweet.tid = tweet_tag.tid
JOIN twitter_user AS tUser ON tUser.screen_name = tweet.posting_user
WHERE tUser.state != "" and tUser.state != "na" and year(tweet.posted) = '2016'
GROUP BY tweet_tag.tag_name
ORDER BY statenum DESC
LIMIT 5;

-- Q7 where hashtag = "GOP", state = "NC", month = 10, year = 2016, k = 5
select count(twt.tid) as tweets, twitter_user.screen_name, twitter_user.category
from twitter_user
join tweet as twt on twt.posting_user = twitter_user.screen_name
join tweet_tag as tag on tag.tid = twt.tid
where tag.tag_name = "GOPDebate"
and twitter_user.state = "North Carolina"
and twt.posted_month = 2
and twt.posted_year = 2016
group by twitter_user.screen_name
order by count(twt.tid) desc
limit 5;


-- Q9 sub-category = 'GOP' k = 5
SELECT twitter_user.screen_name, twitter_user.subcategory, twitter_user.numFollowers
FROM twitter_user
WHERE twitter_user.subcategory = 'GOP'
ORDER BY twitter_user.numFollowers DESC
LIMIT 5;

-- Q16 where k = 5, month = 2, year = 2016
select twitter_user.screen_name, twitter_user.category, twt.textbody, twt.retweet_count, url.address
from twitter_user
join tweet as twt on twt.posting_user = twitter_user.screen_name
join tweet_url as url on url.tid = twt.tid
where twt.posted_month = 2
and twt.posted_year = 2016
order by twt.retweet_count desc
limit 5;

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

-- Q23 where k = 5, subcategroy = "GOP", month = [1,2,3], year = 2016
select hashtag.tag_name, count(distinct twt.tid) as num_uses
from tweet_tag as hashtag
join tweet as twt on twt.tid = hashtag.tid
join twitter_user as users on users.screen_name = twt.posting_user
where users.subcategory = "GOP"
and year(twt.posted) = 2016
and month(twt.posted) between 1 and 3
group by hashtag.tag_name
order by count(distinct twt.tid) desc
limit 5;