package com.xing.paper.po;

public class SelectionPo {

	private TeacherPo teacher;
	private StudentPo student;
	private TopicPo topic;
	private String processing;
	private String grade;

	public TeacherPo getTeacher() {
		return teacher;
	}

	public void setTeacher(TeacherPo teacher) {
		this.teacher = teacher;
	}

	public StudentPo getStudent() {
		return student;
	}

	public void setStudent(StudentPo student) {
		this.student = student;
	}

	public TopicPo getTopic() {
		return topic;
	}

	public void setTopic(TopicPo topic) {
		this.topic = topic;
	}

	public String getProcessing() {
		return processing;
	}

	public void setProcessing(String processing) {
		this.processing = processing;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	@Override
	public String toString() {
		return "SelectionPo [teacher=" + teacher + ", student=" + student + ", topic=" + topic + ", processing="
				+ processing + ", grade=" + grade + "]";
	}

}
