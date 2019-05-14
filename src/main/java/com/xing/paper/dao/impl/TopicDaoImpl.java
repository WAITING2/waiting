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
import com.xing.paper.dao.TopicDao;
import com.xing.paper.po.DepartmentPo;
import com.xing.paper.po.TeacherPo;
import com.xing.paper.po.TopicPo;

@Repository
public class TopicDaoImpl implements TopicDao{
	
	private static Logger logger = Logger.getLogger(TeacherDaoImpl.class);
	
	@Resource
	ComboPooledDataSource basicDataSource;

	@Override
	public void addTopic(TopicPo topic) {
		String sql = "INSERT INTO tb_topic(f_topic,f_teacher) VALUES(?,?);";
		Connection connection = null;
		try {
			connection = basicDataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, topic.getTopic());
			statement.setString(2, topic.getTeacher().getTeacherNumber());
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
	public void deleteTopic(TopicPo topic) {
		String sql = "DELETE FROM tb_topic WHERE f_id = ?";
		Connection connection = null;
		try {
			connection = basicDataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, topic.getId());
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
	public void upgradeTopic(TopicPo topic) {
		String sql = "UPDATE tb_topic SET f_topic=?,f_teacher=? WHERE f_id=?";
		Connection connection = null;
		try {
			connection = basicDataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, topic.getTopic());
			statement.setString(2, topic.getTeacher().getTeacherNumber());
			statement.setString(3, topic.getId());
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
	public List<TopicPo> queryByTeacher(TeacherPo teacher) {
		String sql = "SELECT tb_topic.f_id , tb_topic.f_topic , tb_teacher.f_teacher_number , tb_teacher.f_teacher_name ,"
				+ " tb_teacher.f_gender , tb_teacher.f_title , tb_teacher.f_phone , tb_teacher.f_password , tb_department.f_id AS f_department_id "
				+ ", tb_department.f_department FROM tb_topic JOIN tb_teacher ON tb_topic.f_teacher = tb_teacher.f_teacher_number "
				+ "JOIN tb_department ON tb_department.f_id = tb_teacher.f_department WHERE tb_topic.f_teacher = ? ";
		Connection connection = null;
		List<TopicPo> list = new ArrayList<TopicPo>();
		try {
			connection = basicDataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, teacher.getTeacherNumber());
			ResultSet result = statement.executeQuery();
			while(result.next()){
				TopicPo topic = new TopicPo();
				topic.setId(result.getString("f_id"));
				topic.setTeacher(teacher);
				topic.setTopic(result.getString("f_topic"));
				list.add(topic);
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
		return list;
	}

	@Override
	public TopicPo queryByTopicId(String id) {
		String sql = "SELECT tb_topic.f_id , tb_topic.f_topic , tb_teacher.f_teacher_number , tb_teacher.f_teacher_name ,"
				+ " tb_teacher.f_gender , tb_teacher.f_title , tb_teacher.f_phone , tb_teacher.f_password , tb_department.f_id AS f_department_id "
				+ ", tb_department.f_department FROM tb_topic JOIN tb_teacher ON tb_topic.f_teacher = tb_teacher.f_teacher_number "
				+ "JOIN tb_department ON tb_department.f_id = tb_teacher.f_department WHERE tb_topic.f_id=?;";
		Connection connection = null;
		try {
			connection = basicDataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, id);
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
				TopicPo topic = new TopicPo();
				topic.setId(result.getString("f_id"));
				topic.setTeacher(teacher);
				topic.setTopic(result.getString("f_topic"));
				return topic;
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
	public List<TopicPo> getAllTopic() {
		String sql = "SELECT tb_topic.f_id , tb_topic.f_topic , tb_teacher.f_teacher_number , tb_teacher.f_teacher_name ,"
				+ " tb_teacher.f_gender , tb_teacher.f_title , tb_teacher.f_phone , tb_teacher.f_password , tb_department.f_id AS f_department_id "
				+ ", tb_department.f_department FROM tb_topic JOIN tb_teacher ON tb_topic.f_teacher = tb_teacher.f_teacher_number "
				+ "JOIN tb_department ON tb_department.f_id = tb_teacher.f_department;";
		Connection connection = null;
		List<TopicPo> res = new ArrayList<TopicPo>();
		try {
			connection = basicDataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			Map<String,DepartmentPo> departmentMap = new HashMap<String,DepartmentPo>();
			Map<String,TeacherPo> teacherMap = new HashMap<String,TeacherPo>();
			ResultSet result = statement.executeQuery();
			while(result.next()){
				if(!departmentMap.containsKey(result.getString("f_department_id"))){
					DepartmentPo dept = new DepartmentPo();
					dept.setDepartmentId(result.getString("f_department_id"));
					dept.setDepartmentName(result.getString("f_department"));
					departmentMap.put(dept.getDepartmentId(), dept);
				}
				if(!teacherMap.containsKey(result.getString("f_teacher_number"))){
					TeacherPo teacher = new TeacherPo();
					teacher.setDepartment(departmentMap.get(result.getString("f_department_id")));
					teacher.setGender(result.getString("f_gender"));
					teacher.setPassword(result.getString("f_password"));
					teacher.setPhone(result.getString("f_phone"));
					teacher.setTeacherName(result.getString("f_teacher_name"));
					teacher.setTeacherNumber(result.getString("f_teacher_number"));
					teacher.setTitle(result.getString("f_title"));
					teacherMap.put(teacher.getTeacherNumber(), teacher);
				}
				TopicPo topic = new TopicPo();
				topic.setId(result.getString("f_id"));
				topic.setTeacher(teacherMap.get(result.getString("f_teacher_number")));
				topic.setTopic(result.getString("f_topic"));
				res.add(topic);
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

	@Override
	public List<TopicPo> getNotSelectedTopic() {

		String sql = "SELECT tb_topic.f_id , tb_topic.f_topic , tb_teacher.f_teacher_number , tb_teacher.f_teacher_name , "
				+ "tb_teacher.f_gender , tb_teacher.f_title , tb_teacher.f_phone , tb_teacher.f_password , tb_department.f_id "
				+ "AS f_department_id , tb_department.f_department FROM tb_topic"
				+ " JOIN tb_teacher ON tb_topic.f_teacher = tb_teacher.f_teacher_number "
				+ "JOIN tb_department ON tb_department.f_id = tb_teacher.f_department "
				+ "LEFT OUTER JOIN tb_selection ON tb_selection.f_topic = tb_topic.f_id "
				+ "WHERE tb_selection.f_topic IS NULL;";
		Connection connection = null;
		List<TopicPo> res = new ArrayList<TopicPo>();
		try {
			connection = basicDataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			Map<String,DepartmentPo> departmentMap = new HashMap<String,DepartmentPo>();
			Map<String,TeacherPo> teacherMap = new HashMap<String,TeacherPo>();
			ResultSet result = statement.executeQuery();
			while(result.next()){
				if(!departmentMap.containsKey(result.getString("f_department_id"))){
					DepartmentPo dept = new DepartmentPo();
					dept.setDepartmentId(result.getString("f_department_id"));
					dept.setDepartmentName(result.getString("f_department"));
					departmentMap.put(dept.getDepartmentId(), dept);
				}
				if(!teacherMap.containsKey(result.getString("f_teacher_number"))){
					TeacherPo teacher = new TeacherPo();
					teacher.setDepartment(departmentMap.get(result.getString("f_department_id")));
					teacher.setGender(result.getString("f_gender"));
					teacher.setPassword(result.getString("f_password"));
					teacher.setPhone(result.getString("f_phone"));
					teacher.setTeacherName(result.getString("f_teacher_name"));
					teacher.setTeacherNumber(result.getString("f_teacher_number"));
					teacher.setTitle(result.getString("f_title"));
					teacherMap.put(teacher.getTeacherNumber(), teacher);
				}
				TopicPo topic = new TopicPo();
				topic.setId(result.getString("f_id"));
				topic.setTeacher(teacherMap.get(result.getString("f_teacher_number")));
				topic.setTopic(result.getString("f_topic"));
				res.add(topic);
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
