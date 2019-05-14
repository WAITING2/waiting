package com.xing.paper.service;

import java.util.List;

import com.xing.paper.po.TeacherPo;

public interface TeacherService {
	
	public void addTeacher(TeacherPo teacher);
	public void upgradeTeacher(TeacherPo teacher);
	public void deleteTeacher(TeacherPo teacher);
	public TeacherPo queryByTeacherNumber(String teacherNumber);
	public boolean comfirmLogin(String username,String password);
	public List<TeacherPo> getAllTeacher();

}
