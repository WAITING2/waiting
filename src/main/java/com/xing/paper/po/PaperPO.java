package com.xing.paper.po;

import java.util.Date;

//论文实体
public class PaperPO {
	private Integer id;
	private String paper_content;//论文内容
	private String f_student_number;//学生编号
	private String f_teacher_number;//老湿的id
	private Integer paper_status;//论文状态：0，合格  1，不合格  2，批审中
	private String comment;//评语
	private String editorValue;//论文修改后的结果
	private TeacherPo teacher;
	private StudentPo student;
	private Date posted_time;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPaper_content() {
		return paper_content;
	}
	public void setPaper_content(String paper_content) {
		this.paper_content = paper_content;
	}
	public String getF_student_number() {
		return f_student_number;
	}
	public void setF_student_number(String f_student_number) {
		this.f_student_number = f_student_number;
	}
	public Integer getPaper_status() {
		return paper_status;
	}
	public void setPaper_status(Integer paper_status) {
		this.paper_status = paper_status;
	}
	public String getF_teacher_number() {
		return f_teacher_number;
	}
	public void setF_teacher_number(String f_teacher_number) {
		this.f_teacher_number = f_teacher_number;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getEditorValue() {
		return editorValue;
	}
	public void setEditorValue(String editorValue) {
		this.editorValue = editorValue;
	}
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

	public Date getPosted_time() {
		return posted_time;
	}

	public void setPosted_time(Date posted_time) {
		this.posted_time = posted_time;
	}
}
