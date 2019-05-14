package com.xing.paper.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xing.paper.dao.TeacherDao;
import com.xing.paper.po.TeacherPo;
import com.xing.paper.service.TeacherService;

@Service
public class TeacherServiceImpl implements TeacherService {
	
	@Resource
	TeacherDao teacherDao;

	@Override
	public void addTeacher(TeacherPo teacher) {
		teacherDao.addTeacher(teacher);
	}

	@Override
	public void upgradeTeacher(TeacherPo teacher) {
		teacherDao.upgradeTeacher(teacher);
	}

	@Override
	public void deleteTeacher(TeacherPo teacher) {
		teacherDao.deleteTeacher(teacher);
	}

	@Override
	public TeacherPo queryByTeacherNumber(String teacherNumber) {
		return teacherDao.queryByTeacherNumber(teacherNumber);
	}

	@Override
	public List<TeacherPo> getAllTeacher() {
		return teacherDao.getAllTeacher();
	}

	@Override
	public boolean comfirmLogin(String username, String password) {
		TeacherPo teacher = teacherDao.queryByPassword(username, password);
		return (teacher!=null);
	}

}
