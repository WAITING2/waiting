package com.xing.paper.vo;

import java.util.Date;

public class PaperVO {
	private Integer id;
	private String topic;
	private String studentName;
	private String studentNumber;
	private String teacherName;
	private Integer paperStatus;
	private Integer paperGrade;
	private String department;
	private String className;
	private Date posted_time;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getStudentNumber() {
		return studentNumber;
	}
	public void setStudentNumber(String studentNumber) {
		this.studentNumber = studentNumber;
	}
	public Integer getPaperGrade() {
		return paperGrade;
	}
	public void setPaperGrade(Integer paperGrade) {
		this.paperGrade = paperGrade;
	}

	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	public Integer getPaperStatus() {
		return paperStatus;
	}
	public void setPaperStatus(Integer paperStatus) {
		this.paperStatus = paperStatus;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}

	public Date getPosted_time() {
		return posted_time;
	}

	public void setPosted_time(Date posted_time) {
		this.posted_time = posted_time;
	}
}
