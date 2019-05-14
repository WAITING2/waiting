package com.xing.paper.dao;

import java.util.List;

import com.xing.paper.po.TeacherPo;

public interface TeacherDao {
	
	public void addTeacher(TeacherPo teacher);
	public void upgradeTeacher(TeacherPo teacher);
	public void deleteTeacher(TeacherPo teacher);
	public TeacherPo queryByTeacherNumber(String teacherNumber);
	public TeacherPo queryByPassword(String username,String password);
	public List<TeacherPo> getAllTeacher();
}
