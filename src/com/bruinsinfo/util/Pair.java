package com.bruinsinfo.util;

import java.sql.Timestamp;

public class Pair {
	private String email;
	private Timestamp timestamp;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Timestamp getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	
	public Pair(String e, Timestamp t) {
		email = e;
		timestamp = t;
	}
}
