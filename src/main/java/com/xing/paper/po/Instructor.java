package com.xing.paper.po;

public class Instructor {
	private String instructorNumber;
	private String instructorName;
	private String password;
	private String gender;
	
	public Instructor() {
		super();
	}
	
	
	public Instructor(String instructorNumber, String instructorName, String password, String gender) {
		super();
		this.instructorNumber = instructorNumber;
		this.instructorName = instructorName;
		this.password = password;
		this.gender = gender;
	}

	public String getInstructorNumber() {
		return instructorNumber;
	}
	public void setInstructorNumber(String instructorNumber) {
		this.instructorNumber = instructorNumber;
	}
	public String getInstructorName() {
		return instructorName;
	}
	public void setInstructorName(String instructorName) {
		this.instructorName = instructorName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	
}
