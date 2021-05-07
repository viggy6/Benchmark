package com.mytoolbase.JavaBE.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class People implements Comparable<People>{
	@Id
	private String _id;
	private String padding;
	private LocalDateTime signup;
	private  Long points;
	private String name;
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public String getPadding() {
		return padding;
	}
	public void setPadding(String padding) {
		this.padding = padding;
	}
	public LocalDateTime getSignup() {
		return signup;
	}
	public void setSignup(LocalDateTime signup) {
		this.signup = signup;
	}
	public Long getPoints() {
		return points;
	}
	public void setPoints(Long points) {
		this.points = points;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public int compareTo(People o) {
		// TODO Auto-generated method stub
		return (int) (this.getPoints()-o.getPoints());
	}

}
