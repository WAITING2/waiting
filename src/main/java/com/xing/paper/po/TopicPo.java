package com.xing.paper.po;

public class TopicPo {

	private String id;
	private String topic;
	private TeacherPo teacher;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public TeacherPo getTeacher() {
		return teacher;
	}

	public void setTeacher(TeacherPo teacher) {
		this.teacher = teacher;
	}

}
