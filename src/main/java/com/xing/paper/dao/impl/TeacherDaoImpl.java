package com.xing.paper.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.xing.paper.dao.TeacherDao;
import com.xing.paper.po.DepartmentPo;
import com.xing.paper.po.TeacherPo;

@Repository
public class TeacherDaoImpl implements TeacherDao {

	private static Logger logger = Logger.getLogger(TeacherDaoImpl.class);

	@Resource
	ComboPooledDataSource basicDataSource;

	@Override
	public void addTeacher(TeacherPo teacher) {
		String sql = "INSERT INTO tb_teacher(f_teacher_number,f_teacher_name,f_gender,f_title,"
				+ "f_department,f_phone,f_password) VALUES(?,?,?,?,?,?,?);";
		Connection connection = null;
		try {
			connection = basicDataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, teacher.getTeacherNumber());
			statement.setString(2, teacher.getTeacherName());
			statement.setString(3, teacher.getGender());
			statement.setString(4, teacher.getTitle());
			statement.setString(5, teacher.getDepartment().getDepartmentId());
			statement.setString(6, teacher.getPhone());
			statement.setString(7, teacher.getPassword());
			statement.executeUpdate();
			connection.close();
		} catch (SQLException e) {
			logger.error("Exception", e);
			try {
				connection.close();
			} catch (SQLException e1) {
				logger.error("Exception", e);
			}
		}

	}

	@Override
	public void upgradeTeacher(TeacherPo teacher) {
		String sql = "UPDATE tb_teacher SET f_teacher_name=?,f_gender=?,f_title=?,f_department=?,"
				+ "f_phone=?,f_password=? WHERE f_teacher_number=?;";
		Connection connection = null;
		try {
			connection = basicDataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, teacher.getTeacherName());
			statement.setString(2, teacher.getGender());
			statement.setString(3, teacher.getTitle());
			statement.setString(4, teacher.getDepartment().getDepartmentId());
			statement.setString(5, teacher.getPhone());
			statement.setString(6, teacher.getPassword());
			statement.setString(7, teacher.getTeacherNumber());
			statement.executeUpdate();
			connection.close();
		} catch (SQLException e) {
			logger.error("Exception", e);
			try {
				connection.close();
			} catch (SQLException e1) {
				logger.error("Exception", e);
			}
		}
	}

	@Override
	public void deleteTeacher(TeacherPo teacher) {
		String sql = "DELETE FROM tb_teacher WHERE f_teacher_number=?";
		Connection connection = null;
		try {
			connection = basicDataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, teacher.getTeacherNumber());
			statement.executeUpdate();
			connection.close();
		} catch (SQLException e) {
			logger.error("Exception", e);
			try {
				connection.close();
			} catch (SQLException e1) {
				logger.error("Exception", e);
			}
		}
	}

	@Override
	public TeacherPo queryByTeacherNumber(String teacherNumber) {
		String sql = "SELECT f_teacher_number , f_teacher_name , f_gender , "
				+ "f_title , f_phone , f_password , tb_department.f_id AS f_department_id ,"
				+ "tb_department.f_department FROM tb_teacher JOIN tb_department "
				+ "ON tb_department.f_id = tb_teacher.f_department WHERE f_teacher_number=? ;";
		Connection connection = null;
		try {
			connection = basicDataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, teacherNumber);
			logger.debug(statement);
			ResultSet result = statement.executeQuery();
			if(result.next()){
				DepartmentPo dept = new DepartmentPo();
				dept.setDepartmentId(result.getString("f_department_id"));
				dept.setDepartmentName(result.getString("f_department"));
				TeacherPo teacher = new TeacherPo();
				teacher.setDepartment(dept);
				teacher.setGender(result.getString("f_gender"));
				teacher.setPassword(result.getString("f_password"));
				teacher.setPhone(result.getString("f_phone"));
				teacher.setTeacherName(result.getString("f_teacher_name"));
				teacher.setTeacherNumber(result.getString("f_teacher_number"));
				teacher.setTitle(result.getString("f_title"));
				connection.close();
				return teacher;
			}
			connection.close();
		} catch (SQLException e) {
			logger.error("Exception", e);
			try {
				connection.close();
			} catch (SQLException e1) {
				logger.error("Exception", e);
			}
		}
		return null;
	}

	@Override
	public TeacherPo queryByPassword(String username, String password) {
		String sql = "SELECT f_teacher_number , f_teacher_name , f_gender , "
				+ "f_title , f_phone , f_password , tb_department.f_id AS f_department_id ,"
				+ "tb_department.f_department FROM tb_teacher JOIN tb_department "
				+ "ON tb_department.f_id = tb_teacher.f_department WHERE f_teacher_number = ? AND f_password = ?;";
		Connection connection = null;
		try {
			connection = basicDataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, username);
			statement.setString(2, password);
			ResultSet result = statement.executeQuery();
			if(result.next()){
				DepartmentPo dept = new DepartmentPo();
				dept.setDepartmentId(result.getString("f_department_id"));
				dept.setDepartmentName(result.getString("f_department"));
				TeacherPo teacher = new TeacherPo();
				teacher.setDepartment(dept);
				teacher.setGender(result.getString("f_gender"));
				teacher.setPassword(result.getString("f_password"));
				teacher.setPhone(result.getString("f_phone"));
				teacher.setTeacherName(result.getString("f_teacher_name"));
				teacher.setTeacherNumber(result.getString("f_teacher_number"));
				teacher.setTitle(result.getString("f_title"));
				connection.close();
				return teacher;
			}
			connection.close();
		} catch (SQLException e) {
			logger.error("Exception", e);
			try {
				connection.close();
			} catch (SQLException e1) {
				logger.error("Exception", e);
			}
		}
		return null;
	}

	@Override
	public List<TeacherPo> getAllTeacher() {
		String sql = "SELECT f_teacher_number , f_teacher_name , f_gender , "
				+ "f_title , f_phone , f_password , tb_department.f_id AS f_department_id ,"
				+ "tb_department.f_department FROM tb_teacher JOIN tb_department "
				+ "ON tb_department.f_id = tb_teacher.f_department;";
		Connection connection = null;
		Map<String, DepartmentPo> departmentMap = new HashMap<String, DepartmentPo>();
		List<TeacherPo> res = new ArrayList<TeacherPo>();
		try {
			connection = basicDataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				if (!departmentMap.containsKey(result.getString("f_department_id"))) {
					DepartmentPo dept = new DepartmentPo();
					dept.setDepartmentId(result.getString("f_department_id"));
					dept.setDepartmentName(result.getString("f_department"));
					departmentMap.put(dept.getDepartmentId(), dept);
				}
				TeacherPo teacher = new TeacherPo();
				teacher.setDepartment(departmentMap.get(result.getString("f_department_id")));
				teacher.setGender(result.getString("f_gender"));
				teacher.setPassword(result.getString("f_password"));
				teacher.setPhone(result.getString("f_phone"));
				teacher.setTeacherName(result.getString("f_teacher_name"));
				teacher.setTeacherNumber(result.getString("f_teacher_number"));
				teacher.setTitle(result.getString("f_title"));
				res.add(teacher);
			}
			connection.close();
		} catch (SQLException e) {
			logger.error("Exception", e);
			try {
				connection.close();
			} catch (SQLException e1) {
				logger.error("Exception", e);
			}
		}
		return res;
	}

}
