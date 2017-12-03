package com.bruinsinfo.util;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

public class UserCount {
	private static HashMap<String, ArrayList<Pair>> landmark_user_count = new HashMap<>();
	private static HashMap<String, HashSet<String>> landmark_user = new HashMap<>();
	
	// used for testing
	public static void main(String[] args) {
		Timestamp t1 = Timestamp.valueOf("2017-11-25 13:55:0.0");
		Timestamp t2 = Timestamp.valueOf("2017-11-25 13:57:0.0");
		Timestamp t3 = Timestamp.valueOf("2017-11-25 16:25:0.0");
		Timestamp t4 = Timestamp.valueOf("2017-11-25 16:30:0.0");
		addUser("palm court", "12", t1);
		addUser("palm court", "12", t2);
		addUser("palm court", "123", t3);
		addUser("palm court", "123", t4);
		System.out.println(getUserCount("palm court"));
		for (int i = 0; i < landmark_user_count.get("palm court").size(); ++i) {
			System.out.println(landmark_user_count.get("palm court").get(i).getEmail() + " " + landmark_user_count.get("palm court").get(i).getTimestamp());
		}
	}
	
	// used for testing
	public static void addUser(String landmark, String email, Timestamp t) {
		if (!landmark_user_count.containsKey(landmark)) landmark_user_count.put(landmark, new ArrayList<Pair>());
		if (!landmark_user.containsKey(landmark)) landmark_user.put(landmark, new HashSet<String>());
		if (landmark_user.get(landmark).contains(email)) return;
		landmark_user_count.get(landmark).add(new Pair(email, t));
		landmark_user.get(landmark).add(email);
	}
	
	public static void addUser(String landmark, String email) {
		Date date = new Date();
		Timestamp t = new Timestamp(date.getTime());
		if (!landmark_user_count.containsKey(landmark)) landmark_user_count.put(landmark, new ArrayList<Pair>());
		if (!landmark_user.containsKey(landmark)) landmark_user.put(landmark, new HashSet<String>());
		if (landmark_user.get(landmark).contains(email)) return;
		landmark_user_count.get(landmark).add(new Pair(email, t));
		landmark_user.get(landmark).add(email);
	}
	
	public static HashMap<Integer, HashSet<String>> getUserCount(String landmark) {
		// first update the landmark_user_count
		if (!landmark_user_count.containsKey(landmark)) {
			HashMap<Integer, HashSet<String>> nolandmark = new HashMap<>();
			HashSet<String> emptyset = new HashSet<>();
			nolandmark.put(0, emptyset);
			return nolandmark;
		}
		//used to be return 0
		ArrayList<Pair> user_timestamp = landmark_user_count.get(landmark);
		HashSet<String> user = landmark_user.get(landmark);
		
		Date date = new Date();
		Timestamp cur = new Timestamp(date.getTime());
		long milliseconds_cur = cur.getTime();
		int i = 0;
		for (; i < user_timestamp.size(); ++i) {
			Timestamp t = user_timestamp.get(i).getTimestamp();
			long milliseconds_t = t.getTime();
			long diff = milliseconds_cur - milliseconds_t;
			long diffMinutes = diff / (60 * 1000);
			if (diffMinutes <= 20) break;
		}
		while (i-- != 0) {
			String email = user_timestamp.get(0).getEmail();
			user.remove(email);
			user_timestamp.remove(0); 
		}
		
		//return data object that also holds email list of users in range
		
		HashMap<Integer, HashSet<String>> user_email_list = new HashMap<>();
		int num_users = user_timestamp.size();
		user_email_list.put(num_users, user);
		
		return user_email_list; 
		//return user_timestamp.size();
	}
}
