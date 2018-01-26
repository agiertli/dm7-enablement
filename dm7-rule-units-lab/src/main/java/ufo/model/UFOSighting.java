package ufo.model;

import java.time.LocalDateTime;

public class UFOSighting {

	private LocalDateTime dateTime;
	private String city;
	private String state;
	private String country;
	private String shape;
	private Integer seconds;
	private String duration;
	private String comments;

	public UFOSighting() {
	};

	public UFOSighting(LocalDateTime dateTime, String city, String state, String country, String shape, Integer seconds,
			String duration, String comments) {
		this.dateTime = dateTime;
		this.city = city;
		this.state = state;
		this.country = country;
		this.shape = shape;
		this.seconds = seconds;
		this.duration = duration;
		this.comments = comments;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getShape() {
		return shape;
	}

	public void setShape(String shape) {
		this.shape = shape;
	}

	public Integer getSeconds() {
		return seconds;
	}

	public void setSeconds(Integer seconds) {
		this.seconds = seconds;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

}
