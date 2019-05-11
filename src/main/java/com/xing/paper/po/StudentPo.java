package com.xing.paper.po;

public class StudentPo {

	private String studentNumber;
	private String studentName;
	private String gender;
	private DepartmentPo department;
	private String className;
	private String phone;
	private String password;
	private String email;
	private Integer paper_flag;
	private ClassPO classPO;
	private TeacherPo teacherPo;

	public String getStudentNumber() {
		return studentNumber;
	}

	public void setStudentNumber(String studentNumber) {
		this.studentNumber = studentNumber;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public DepartmentPo getDepartment() {
		return department;
	}

	public void setDepartment(DepartmentPo department) {
		this.department = department;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getPaper_flag() {
		return paper_flag;
	}

	public void setPaper_flag(Integer paper_flag) {
		this.paper_flag = paper_flag;
	}


	public void setClassPO(ClassPO classPO) {
		this.classPO = classPO;
	}

	public ClassPO getClassPO() {
		return classPO;
	}

	public TeacherPo getTeacherPo() {
		return teacherPo;
	}

	public void setTeacherPo(TeacherPo teacherPo) {
		this.teacherPo = teacherPo;
	}
}
