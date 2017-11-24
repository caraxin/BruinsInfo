package com.bruinsinfo.model;

public class Landmark {
	private String name;
	private double latitude;
	private double longitude;
	private String url;
	private double distance;
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public Landmark(String name, double latitude, double longitude, String url, double distance) {
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
		this.url = url;
		this.distance = distance;
	}
	
}
