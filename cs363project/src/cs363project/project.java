package cs363project;

import java.sql.*;
import java.util.Scanner;

/**
 * @author tmorvant
 *
 */
public class project {

	
	public static void main(String[] args) {
		String dbServer = "jdbc:mysql://localhost:3306/group89?useSSL=false";
		// For compliance with existing applications not using SSL the verifyServerCertificate property is set to ‘false’,
		String userName = "";
		String password = "";


		userName = "cs363";		//connection to database on these credentials
		password = "363F2020";

		Connection conn;
		Statement stmt;

		System.out.println("Type letter to select option and press enter");
		System.out.println("");
		//try/catch loop to open and run connection
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(dbServer, userName, password);
			stmt = conn.createStatement();
//			String sqlQuery = "";

//			String option = "";
			Scanner scanner = new Scanner(System.in);
			System.out.println("Enter option letter");
			System.out.println("Options:");
			System.out.println("	a: Perform functionality Q3");
			System.out.println("	b: Perform functionality Q7");
			System.out.println("	c: Perfrom functionality Q9");
			System.out.println("	d: Perform functionality Q16");
			System.out.println("	e: Perfrom functionality Q18");
			System.out.println("	f: Perfrom functionality Q23");
			System.out.println("	g: Add twitter user to database");
			System.out.println("	h: Delete twitter user from database");
			System.out.println("	x: Exit \n");
			
			String selection = scanner.nextLine();
			
			while (!selection.equals("x")) {
				if(selection.equals("a")) {
					System.out.println("Select year: ");
					int year = scanner.nextInt();
					scanner.nextLine();
					System.out.println("Select number of results:");
					int num = scanner.nextInt();
					scanner.nextLine();
					findMostUsedHashtags(num, year, stmt);
					System.out.println("Select next option:");
					selection = scanner.nextLine();
				}
				else if (selection.equals("b")) {
					System.out.println("Enter hashtag (without the #):");
					String hashtag = scanner.nextLine();
					System.out.println("Enter state name (full name):");
					String state = scanner.nextLine();
					System.out.println("Enter month number (1-12):");
					int month = scanner.nextInt();
					scanner.nextLine();
					System.out.println("Enter year:");
					int year = scanner.nextInt();
					scanner.nextLine();
					System.out.println("Enter number of results:");
					int num = scanner.nextInt();
					scanner.nextLine();
					findUserByHashtag(hashtag, state, num, month, year, stmt);
					System.out.println("Select next option:");
					selection = scanner.nextLine();
				}
				else if (selection.equals("c")) {
					System.out.println("Enter user subcategory:");
					String subcategory = scanner.nextLine();
					System.out.println("Enter number of results:");
					int num = scanner.nextInt();
					scanner.nextLine();
					findMostFollowed(subcategory, num, stmt);
					System.out.println("Select next option:");
					selection = scanner.nextLine();
				}
				else if (selection.equals("d")) {
					System.out.println("Enter month (1-12):");
					int month = scanner.nextInt();
					scanner.nextLine();
					System.out.println("Enter year:");
					int year = scanner.nextInt();
					scanner.nextLine();
					System.out.println("Enter number of results:");
					int num = scanner.nextInt();
					scanner.nextLine();
					findMostRetweeted(month, year, num, stmt);
					System.out.println("Select next option:");
					selection = scanner.nextLine();
				}
				else if (selection.equals("e")) {
					System.out.println("Enter subcategory:");
					String subcategory = scanner.nextLine();
					System.out.println("Enter month (1-12):");
					int month = scanner.nextInt();
					scanner.nextLine();
					System.out.println("Enter year:");
					int year = scanner.nextInt();
					scanner.nextLine();
					System.out.println("Enter number of results:");
					int num = scanner.nextInt();
					scanner.nextLine();
					findMostMentioned(subcategory, month, year, num, stmt);
					System.out.println("Select next option:");
					selection = scanner.nextLine();
				}
				else if (selection.equals("f")) {
					System.out.println("Enter subcategory:");
					String subcategory = scanner.nextLine();
					int[] months = new int[2];
					System.out.println("Enter start month (1-12)");
					months[0] = scanner.nextInt();
					scanner.nextLine();
					System.out.println("Enter end month (1-12):");
					months[1] = scanner.nextInt();
					scanner.nextLine();
					System.out.println("Enter year:");
					int year = scanner.nextInt();
					scanner.nextLine();
					System.out.println("Enter number of results:");
					int num = scanner.nextInt();
					scanner.nextLine();
					findMostUsedHashtags(subcategory, months, year, num, stmt);
					System.out.println("Select next option:");
					selection = scanner.nextLine();
				}
				else if (selection.equals("g")) {
					System.out.println("Enter screen name of new user:");
					String screenname = scanner.nextLine();
					System.out.println("Enter name of user:");
					String name = scanner.nextLine();
					System.out.println("Enter users number of followers:");
					int followers = scanner.nextInt();
					scanner.nextLine();
					System.out.println("Enter number of users this user is following:");
					int following = scanner.nextInt();
					scanner.nextLine();
					System.out.println("Enter user's category:");
					String category = scanner.nextLine();
					System.out.println("Enter user's sub-category:");
					String subcategory = scanner.nextLine();
					System.out.println("Enter user's state:");
					String state = scanner.nextLine();
					addUser(screenname, name, followers, following, category, subcategory, state, conn);
					System.out.println("Select next option:");
					selection = scanner.nextLine();
				}
				else if (selection.equals("h")) {
					System.out.println("Enter user's screen name to delete:");
					String screenname = scanner.nextLine();
					deleteUser(screenname, conn);
					System.out.println("Select next option:");
					selection = scanner.nextLine();
				}
				else {
					System.out.println("Invalid selection, try again");
					
					selection = scanner.nextLine();
				}
			}

			System.out.println("Exited");
			stmt.close();	//closes statement
			conn.close();	//closes connection
			scanner.close();
		} catch (Exception e) {
			System.out.println("Program terminates due to errors");
			e.printStackTrace();	//throws sql exception if something does not go as planned
		}
	}
	
	/**
	 * Finds k most used hashtags in given states and how many states they were used in
	 * 
	 * @param k				The number of results to return
	 * @param year			The year from which to find results
	 * @param stmt			Statement to pass into runQuery 
	 * @throws SQLException
	 */
	private static void findMostUsedHashtags(int k, int year, Statement stmt) throws SQLException {
		String query = "SELECT COUNT(DISTINCT tUser.state) AS statenum, group_concat(DISTINCT tUser.state ORDER BY tUser.state) as States, tweet_tag.tag_name as name "
				+ "FROM tweet_tag "
				+ "JOIN tweet ON tweet.tid = tweet_tag.tid "
				+ "JOIN twitter_user AS tUser ON tUser.screen_name = tweet.posting_user "
				+ "WHERE tUser.state != \"\" and tUser.state != \"na\" and year(tweet.posted) = '" + year + "' "
				+ "GROUP BY tweet_tag.tag_name "
				+ "ORDER BY statenum DESC "
				+ "LIMIT " + k + ";";
		runQuery(stmt, query);
		
	}
	
	/**
	 * Find k users who used a given hashtag in a given state in a given year from a given state
	 * 
	 * @param hashtag		Hashtag to search
	 * @param state			User's state
	 * @param k				Number of results to return
	 * @param month			Month tweeted
	 * @param year			Year tweeted
	 * @param stmt			Statement to pass into runQuery
	 * @throws SQLException
	 */
	private static void findUserByHashtag(String hashtag, String state, int k, int month, int year, Statement stmt) throws SQLException {
		String query = "select count(twt.tid) as tweets, twitter_user.screen_name, twitter_user.category "
				+ "from twitter_user "
				+ "join tweet as twt on twt.posting_user = twitter_user.screen_name "
				+ "join tweet_tag as tag on tag.tid = twt.tid "
				+ "where tag.tag_name = \"" + hashtag + "\" "
				+ "and twitter_user.state = \"" + state + "\" "
				+ "and month(twt.posted) = " + month + " "
				+ "and year(twt.posted) = " + year + " "
				+ "group by twitter_user.screen_name "
				+ "order by count(twt.tid) desc "
				+ "limit " + k + ";";
		runQuery(stmt, query);
	}
	
	/**
	 * Returns k most followed users from given category
	 * 
	 * @param category		Category from which to find users
	 * @param k				Number of results
	 * @param stmt			Statement to pass into runQuery
	 * @throws SQLException
	 */
	private static void findMostFollowed(String category, int k, Statement stmt) throws SQLException {
		String query = "SELECT twitter_user.screen_name, twitter_user.subcategory, twitter_user.numFollowers "
				+ "FROM twitter_user "
				+ "WHERE twitter_user.subcategory = '" + category + "' "
				+ "ORDER BY twitter_user.numFollowers DESC "
				+ "LIMIT " + k + ";";
		runQuery(stmt, query);
	}
	
	/**
	 * Find k most retweeted tweets during given month of given year
	 * 
	 * @param month			Month from which to find tweets
	 * @param year			Year from which to find tweets
	 * @param k				Number of results
	 * @param stmt			Statement to pass into runQuery
	 * @throws SQLException
	 */
	private static void findMostRetweeted(int month, int year, int k, Statement stmt) throws SQLException {
		String query = "select twitter_user.screen_name, twitter_user.category, twt.textbody, twt.retweet_count, url.address "
				+ "from twitter_user "
				+ "join tweet as twt on twt.posting_user = twitter_user.screen_name "
				+ "join tweet_url as url on url.tid = twt.tid "
				+ "where month(twt.posted) = " + month + " "
				+ "and year(twt.posted) = " + year + " "
				+ "order by twt.retweet_count desc "
				+ "limit " + k + ";";
		runQuery(stmt, query);
	}
	
	/**
	 * Finds k most mentioned users from the mentionees given subcategory from a given month in a given year
	 * 
	 * @param subcategory	Mentionee's subcategory
	 * @param month			Month from which to find tweets
	 * @param year			Year from which to find tweets
	 * @param k				Number of results
	 * @param stmt			Statement to pass into runQuery
	 * @throws SQLException
	 */
	private static void findMostMentioned(String subcategory, int month, int year, int k, Statement stmt) throws SQLException {
		String query = "SELECT mentioned.screen_name as mentionedUser, mentioned.state as mentionedUserState, group_concat(DISTINCT mentionee.screen_name ORDER BY mentionee.screen_name) as postingUsers "
				+ "FROM twitter_user mentioned "
				+ "JOIN mention AS men ON men.screen_name = mentioned.screen_name "
				+ "JOIN tweet ON tweet.tid = men.tid AND month(tweet.posted) = " + month + " AND year(tweet.posted) = " + year + " "
				+ "JOIN twitter_user AS mentionee ON mentionee.screen_name = tweet.posting_user "
				+ "WHERE mentionee.subcategory = '" + subcategory + "' "
				+ "GROUP BY mentioned.screen_name "
				+ "ORDER BY COUNT(men.tid) DESC "
				+ "LIMIT " + k + ";";
		runQuery(stmt, query);
	}
	
	/**
	 * Finds k most used hashtags from given subcategory of users from a given range of months from a given year
	 * 
	 * @param subcategory	User's subcategory to find tweets/hashtags
	 * @param months		Range of months to find tweets
	 * @param year			Year from which to find tweets
	 * @param k				Number of results
	 * @param stmt			Statement to pass in runQuery
	 * @throws SQLException
	 */
	private static void findMostUsedHashtags(String subcategory, int[] months, int year, int k, Statement stmt) throws SQLException {
		String query = "select hashtag.tag_name, count(distinct twt.tid) as num_uses "
				+ "from tweet_tag as hashtag "
				+ "join tweet as twt on twt.tid = hashtag.tid "
				+ "join twitter_user as users on users.screen_name = twt.posting_user "
				+ "where users.subcategory = \"" + subcategory + "\" "
				+ "and year(twt.posted) = " + year + " "
				+ "and month(twt.posted) between " + months[0] + " and " + months[1] + " "
				+ "group by hashtag.tag_name "
				+ "order by count(distinct twt.tid) desc "
				+ "limit " + k + ";";
		runQuery(stmt, query);
	}
	
	/**
	 * Adds new twitter_user to twitter_user table
	 * 
	 * @param screenname	Screenname of user to add
	 * @param name			Name of user to add
	 * @param followers		Number of followers of user to add
	 * @param following		Number of users that are being followed by user to add
	 * @param category		Category of user to add
	 * @param subcategory	Subcategory of user to add
	 * @param state			State of user to add
	 * @param conn			Connection to run add statement on
	 */
	private static void addUser(String screenname, String name, int followers, int following, String category, String subcategory, String state, Connection conn) {
		try {
			
			
			conn.setAutoCommit(false);
			Statement stmt = conn.createStatement();
			
//			ResultSet rs;
//			int id=0;
			
			// get the maximum id from the actor table
//			rs = stmt.executeQuery("select max(actor_id) from actor");
//			while (rs.next()) {
//				id = rs.getInt(1);
//			}
//			rs.close();
//			stmt.close();
			
			
			PreparedStatement inststmt = conn.prepareStatement(
	                " insert into twitter_user (screen_name,name,numFollowers,numFollowing,category,subcategory,state) values(?,?,?,?,?,?,?) ");
			
			
			inststmt.setString(1, screenname);
			inststmt.setString(2, name);
			inststmt.setInt(3, followers);
			inststmt.setInt(4, following);
			inststmt.setString(5, category);
			inststmt.setString(6, subcategory);
			inststmt.setString(7, state);
			
			
			int rowcount = inststmt.executeUpdate();
			
			System.out.println("User " + screenname + " has been added as a twitter user");
			inststmt.close();
			// confirm that these are the changes you want to make
			conn.commit();
			// if other parts of the program needs commit per SQL statement
			// conn.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Deletes user from twitter_user table, their tweets from tweet table, and their hashtags from tag table
	 * 
	 * @param screenname	Screenname of user to delete
	 * @param conn			Connection to run delete statement on
	 */
	private static void deleteUser(String screenname, Connection conn) {
		try {
			conn.setAutoCommit(false);	//won't immediately delete until commit()
//			Statement stmt = conn.createStatement();
			
			
			//deletes from payment
			PreparedStatement inststmt = conn.prepareStatement(
	                " delete from twitter_user where screen_name = ?");	//delete statement to be executed
			
			inststmt.setString(1, screenname);	//sets screenname at the ?
			
			int rowcount = inststmt.executeUpdate();	//executes update
			
			System.out.println("User " + screenname + " and all relating tweets and hashtags have been deleted");	//returns rows updated
			inststmt.close();	//closes statement
			
			conn.commit();	//commits updates to database
		}
		catch (SQLException e) {
		}
		
		
	}
	
	/**
	 * 
	 * Method borrowed from JDBCTransaction tester, written by ISU CS dept. Will run query and print results with modification to print to console
	 * 
	 * @param stmt 				Stmt to run via connection
	 * @param sqlQuery			String on which to run sql query
	 * @throws SQLException		Any exception thrown by database
	 * 
	 */
	private static void runQuery(Statement stmt, String sqlQuery) throws SQLException {
		ResultSet rs;
		ResultSetMetaData rsMetaData;
		String toShow;
		rs = stmt.executeQuery(sqlQuery);
		rsMetaData = rs.getMetaData();
//		System.out.println(sqlQuery);
		toShow = "";
		while (rs.next()) {
			for (int i = 0; i < rsMetaData.getColumnCount(); i++) {
				toShow += rs.getString(i + 1) + ", ";
			}
			toShow += "\n";
		}
		System.out.println(toShow);
	}
	
}