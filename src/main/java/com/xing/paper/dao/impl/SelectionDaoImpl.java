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
import com.xing.paper.dao.SelectionDao;
import com.xing.paper.po.DepartmentPo;
import com.xing.paper.po.SelectionPo;
import com.xing.paper.po.StudentPo;
import com.xing.paper.po.TeacherPo;
import com.xing.paper.po.TopicPo;

@Repository
public class SelectionDaoImpl implements SelectionDao {

	private static Logger logger = Logger.getLogger(SelectionDaoImpl.class);

	@Resource
	ComboPooledDataSource basicDataSource;

	@Override
	public void addSelection(SelectionPo selection) {
		String sql = "INSERT INTO tb_selection(f_student,f_topic,f_processing,f_grade) VALUES (?,?,?,?);";
		Connection connection = null;
		try {
			connection = basicDataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, selection.getStudent().getStudentNumber());
			statement.setString(2, selection.getTopic().getId());
			statement.setString(3, selection.getProcessing());
			statement.setString(4, selection.getGrade());
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
	public void deleteSelection(SelectionPo selection) {
		String sql = "DELETE FROM tb_selection WHERE f_student=? AND f_topic=?;";
		Connection connection = null;
		try {
			connection = basicDataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, selection.getStudent().getStudentNumber());
			statement.setString(2, selection.getTopic().getId());
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
	public void upgradeSelection(SelectionPo selection) {
		String sql = "UPDATE tb_selection SET f_processing=?,f_grade=? WHERE f_student=? AND f_topic=?";
		Connection connection = null;
		try {
			connection = basicDataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, selection.getProcessing());
			statement.setString(2, selection.getGrade());
			statement.setString(3, selection.getStudent().getStudentNumber());
			statement.setString(4, selection.getTopic().getId());
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
	public List<SelectionPo> queryByTeacher(TeacherPo teacher) {
		List<SelectionPo> res = new ArrayList<SelectionPo>();
		String sql = "SELECT tb_selection.f_processing , tb_selection.f_grade , tb_topic.f_id AS f_topic_id , "
				+ "tb_topic.f_topic , tb_teacher.f_teacher_number , tb_teacher.f_teacher_name , "
				+ "tb_teacher.f_gender AS f_teacher_gender , tb_teacher.f_title , tb_teacher.f_phone AS f_teacher_phone ,"
				+ " tb_teacher.f_password AS f_teacher_password , tb_student.f_student_number , "
				+ "tb_student.f_student_name , tb_student.f_gender AS f_student_gender , tb_student.f_class AS f_student_class , "
				+ "tb_student.f_phone AS f_student_phone , tb_student.f_password AS f_student_password , "
				+ "tb_department.f_id AS f_teacher_department_id , tb_department.f_department AS f_teacher_department , "
				+ "dept_2.f_id AS f_student_department_id , dept_2.f_department AS f_student_department "
				+ "FROM tb_selection JOIN tb_topic ON tb_topic.f_id = tb_selection.f_topic "
				+ "JOIN tb_teacher ON tb_topic.f_teacher = tb_teacher.f_teacher_number "
				+ "JOIN tb_student ON tb_selection.f_student = tb_student.f_student_number "
				+ "JOIN tb_department ON tb_teacher.f_department = tb_department.f_id "
				+ "JOIN tb_department dept_2 ON tb_student.f_department = dept_2.f_id "
				+ "WHERE tb_teacher.f_teacher_number = ?;";
		Connection connection = null;
		try {
			connection = basicDataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, teacher.getTeacherNumber());
			ResultSet result = statement.executeQuery();
			Map<String, TopicPo> topicMap = new HashMap<String, TopicPo>();
			Map<String, TeacherPo> teacherMap = new HashMap<String, TeacherPo>();
			Map<String, StudentPo> studentMap = new HashMap<String, StudentPo>();
			Map<String, DepartmentPo> departmentMap = new HashMap<String, DepartmentPo>();
			while (result.next()) {
				if (!departmentMap.containsKey(result.getString("f_teacher_department_id"))) {
					DepartmentPo dept  =  new DepartmentPo();
					dept.setDepartmentId(result.getString("f_teacher_department_id"));
					dept.setDepartmentName(result.getString("f_teacher_department"));
					departmentMap.put(dept.getDepartmentId(), dept);
				}
				
				if (!departmentMap.containsKey(result.getString("f_student_department_id"))) {
					DepartmentPo dept  =  new DepartmentPo();
					dept.setDepartmentId(result.getString("f_student_department_id"));
					dept.setDepartmentName(result.getString("f_student_department_id"));
					departmentMap.put(dept.getDepartmentId(), dept);
				}

				if (!teacherMap.containsKey(result.getString("f_teacher_number"))) {
					TeacherPo teacherPo = new TeacherPo();
					teacherPo.setDepartment(departmentMap.get(result.getString("f_teacher_department_id")));
					teacherPo.setGender(result.getString("f_teacher_gender"));
					teacherPo.setPassword(result.getString("f_teacher_password"));
					teacherPo.setPhone(result.getString("f_teacher_phone"));
					teacherPo.setTeacherName(result.getString("f_teacher_name"));
					teacherPo.setTeacherNumber(result.getString("f_teacher_number"));
					teacherPo.setTitle(result.getString("f_title"));
					teacherMap.put(teacherPo.getTeacherNumber(), teacherPo);
				}
				if (!topicMap.containsKey(result.getString("f_topic_id"))) {
					TopicPo topic = new TopicPo();
					topic.setId(result.getString("f_topic_id"));
					topic.setTeacher(teacherMap.get(result.getString("f_teacher_number")));
					topic.setTopic(result.getString("f_topic"));
					topicMap.put(topic.getId(), topic);
				}
				if (!studentMap.containsKey(result.getString("f_student_number"))) {
					StudentPo student = new StudentPo();
					student.setClassName(result.getString("f_student_class"));
					student.setDepartment(departmentMap.get(result.getString("f_student_department_id")));
					student.setGender(result.getString("f_student_gender"));
					student.setPassword(result.getString("f_student_password"));
					student.setPhone(result.getString("f_student_phone"));
					student.setStudentName(result.getString("f_student_name"));
					student.setStudentNumber(result.getString("f_student_number"));
					studentMap.put(student.getStudentNumber(), student);
				}

				SelectionPo selection = new SelectionPo();
				selection.setStudent(studentMap.get(result.getString("f_student_number")));
				selection.setTeacher(teacherMap.get(result.getString("f_teacher_number")));
				selection.setTopic(topicMap.get(result.getString("f_topic_id")));
				selection.setGrade(result.getString("f_grade"));
				selection.setProcessing(result.getString("f_processing"));
				res.add(selection);
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
	public SelectionPo queryByStudent(StudentPo student) {
		String sql = "SELECT tb_selection.f_processing , tb_selection.f_grade , tb_topic.f_id AS f_topic_id , "
				+ "tb_topic.f_topic , tb_teacher.f_teacher_number , tb_teacher.f_teacher_name , "
				+ "tb_teacher.f_gender AS f_teacher_gender , tb_teacher.f_title , tb_teacher.f_phone AS f_teacher_phone ,"
				+ " tb_teacher.f_password AS f_teacher_password , tb_student.f_student_number , "
				+ "tb_student.f_student_name , tb_student.f_gender AS f_student_gender , tb_student.f_class AS f_student_class , "
				+ "tb_student.f_phone AS f_student_phone , tb_student.f_password AS f_student_password , "
				+ "tb_department.f_id AS f_teacher_department_id , tb_department.f_department AS f_teacher_department , "
				+ "dept_2.f_id AS f_student_department_id , dept_2.f_department AS f_student_department "
				+ "FROM tb_selection JOIN tb_topic ON tb_topic.f_id = tb_selection.f_topic "
				+ "JOIN tb_teacher ON tb_topic.f_teacher = tb_teacher.f_teacher_number "
				+ "JOIN tb_student ON tb_selection.f_student = tb_student.f_student_number "
				+ "JOIN tb_department ON tb_teacher.f_department = tb_department.f_id "
				+ "JOIN tb_department dept_2 ON tb_student.f_department = dept_2.f_id "
				+ "WHERE tb_selection.f_student = ? ;";
		List<SelectionPo> res = new ArrayList<SelectionPo>();
		Connection connection = null;
		try {
			connection = basicDataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, student.getStudentNumber());
			ResultSet result = statement.executeQuery();
			Map<String, TopicPo> topicMap = new HashMap<String, TopicPo>();
			Map<String, TeacherPo> teacherMap = new HashMap<String, TeacherPo>();
			Map<String, StudentPo> studentMap = new HashMap<String, StudentPo>();
			Map<String, DepartmentPo> departmentMap = new HashMap<String, DepartmentPo>();
			while (result.next()) {
				if (!departmentMap.containsKey(result.getString("f_teacher_department_id"))) {
					DepartmentPo dept  =  new DepartmentPo();
					dept.setDepartmentId(result.getString("f_teacher_department_id"));
					dept.setDepartmentName(result.getString("f_teacher_department"));
					departmentMap.put(dept.getDepartmentId(), dept);
				}
				
				if (!departmentMap.containsKey(result.getString("f_student_department_id"))) {
					DepartmentPo dept  =  new DepartmentPo();
					dept.setDepartmentId(result.getString("f_student_department_id"));
					dept.setDepartmentName(result.getString("f_student_department_id"));
					departmentMap.put(dept.getDepartmentId(), dept);
				}

				if (!teacherMap.containsKey(result.getString("f_teacher_number"))) {
					TeacherPo teacherPo = new TeacherPo();
					teacherPo.setDepartment(departmentMap.get(result.getString("f_teacher_department_id")));
					teacherPo.setGender(result.getString("f_teacher_gender"));
					teacherPo.setPassword(result.getString("f_teacher_password"));
					teacherPo.setPhone(result.getString("f_teacher_phone"));
					teacherPo.setTeacherName(result.getString("f_teacher_name"));
					teacherPo.setTeacherNumber(result.getString("f_teacher_number"));
					teacherPo.setTitle(result.getString("f_title"));
					teacherMap.put(teacherPo.getTeacherNumber(), teacherPo);
				}
				if (!topicMap.containsKey(result.getString("f_topic_id"))) {
					TopicPo topic = new TopicPo();
					topic.setId(result.getString("f_topic_id"));
					topic.setTeacher(teacherMap.get(result.getString("f_teacher_number")));
					topic.setTopic(result.getString("f_topic"));
					topicMap.put(topic.getId(), topic);
				}
				if (!studentMap.containsKey(result.getString("f_student_number"))) {
					StudentPo studentPo = new StudentPo();
					studentPo.setClassName(result.getString("f_student_class"));
					studentPo.setDepartment(departmentMap.get(result.getString("f_student_department_id")));
					studentPo.setGender(result.getString("f_student_gender"));
					studentPo.setPassword(result.getString("f_student_password"));
					studentPo.setPhone(result.getString("f_student_phone"));
					studentPo.setStudentName(result.getString("f_student_name"));
					studentPo.setStudentNumber(result.getString("f_student_number"));
					studentMap.put(studentPo.getStudentNumber(), studentPo);
				}

				SelectionPo selection = new SelectionPo();
				selection.setStudent(studentMap.get(result.getString("f_student_number")));
				selection.setTeacher(teacherMap.get(result.getString("f_teacher_number")));
				selection.setTopic(topicMap.get(result.getString("f_topic_id")));
				selection.setGrade(result.getString("f_grade"));
				selection.setProcessing(result.getString("f_processing"));
				res.add(selection);
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
		if(res.isEmpty()){
			return null;
		}else{
			return res.get(0);
		}
	}

	@Override
	public List<SelectionPo> getAllSelection() {
		String sql = "SELECT tb_selection.f_processing , tb_selection.f_grade , tb_topic.f_id AS f_topic_id , "
				+ "tb_topic.f_topic , tb_teacher.f_teacher_number , tb_teacher.f_teacher_name , "
				+ "tb_teacher.f_gender AS f_teacher_gender , tb_teacher.f_title , tb_teacher.f_phone AS f_teacher_phone ,"
				+ " tb_teacher.f_password AS f_teacher_password , tb_student.f_student_number , "
				+ "tb_student.f_student_name , tb_student.f_gender AS f_student_gender , tb_student.f_class AS f_student_class , "
				+ "tb_student.f_phone AS f_student_phone , tb_student.f_password AS f_student_password , "
				+ "tb_department.f_id AS f_teacher_department_id , tb_department.f_department AS f_teacher_department , "
				+ "dept_2.f_id AS f_student_department_id , dept_2.f_department AS f_student_department "
				+ "FROM tb_selection JOIN tb_topic ON tb_topic.f_id = tb_selection.f_topic "
				+ "JOIN tb_teacher ON tb_topic.f_teacher = tb_teacher.f_teacher_number "
				+ "JOIN tb_student ON tb_selection.f_student = tb_student.f_student_number "
				+ "JOIN tb_department ON tb_teacher.f_department = tb_department.f_id "
				+ "JOIN tb_department dept_2 ON tb_student.f_department = dept_2.f_id; ";
		List<SelectionPo> res = new ArrayList<SelectionPo>();
		Connection connection = null;
		try {
			connection = basicDataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet result = statement.executeQuery();
			Map<String, TopicPo> topicMap = new HashMap<String, TopicPo>();
			Map<String, TeacherPo> teacherMap = new HashMap<String, TeacherPo>();
			Map<String, StudentPo> studentMap = new HashMap<String, StudentPo>();
			Map<String, DepartmentPo> departmentMap = new HashMap<String, DepartmentPo>();
			while (result.next()) {
				if (!departmentMap.containsKey(result.getString("f_teacher_department_id"))) {
					DepartmentPo dept  =  new DepartmentPo();
					dept.setDepartmentId(result.getString("f_teacher_department_id"));
					dept.setDepartmentName(result.getString("f_teacher_department"));
					departmentMap.put(dept.getDepartmentId(), dept);
				}
				
				if (!departmentMap.containsKey(result.getString("f_student_department_id"))) {
					DepartmentPo dept  =  new DepartmentPo();
					dept.setDepartmentId(result.getString("f_student_department_id"));
					dept.setDepartmentName(result.getString("f_student_department_id"));
					departmentMap.put(dept.getDepartmentId(), dept);
				}

				if (!teacherMap.containsKey(result.getString("f_teacher_number"))) {
					TeacherPo teacherPo = new TeacherPo();
					teacherPo.setDepartment(departmentMap.get(result.getString("f_teacher_department_id")));
					teacherPo.setGender(result.getString("f_teacher_gender"));
					teacherPo.setPassword(result.getString("f_teacher_password"));
					teacherPo.setPhone(result.getString("f_teacher_phone"));
					teacherPo.setTeacherName(result.getString("f_teacher_name"));
					teacherPo.setTeacherNumber(result.getString("f_teacher_number"));
					teacherPo.setTitle(result.getString("f_title"));
					teacherMap.put(teacherPo.getTeacherNumber(), teacherPo);
				}
				if (!topicMap.containsKey(result.getString("f_topic_id"))) {
					TopicPo topic = new TopicPo();
					topic.setId(result.getString("f_topic_id"));
					topic.setTeacher(teacherMap.get(result.getString("f_teacher_number")));
					topic.setTopic(result.getString("f_topic"));
					topicMap.put(topic.getId(), topic);
				}
				if (!studentMap.containsKey(result.getString("f_student_number"))) {
					StudentPo student = new StudentPo();
					student.setClassName(result.getString("f_student_class"));
					student.setDepartment(departmentMap.get(result.getString("f_student_department_id")));
					student.setGender(result.getString("f_student_gender"));
					student.setPassword(result.getString("f_student_password"));
					student.setPhone(result.getString("f_student_phone"));
					student.setStudentName(result.getString("f_student_name"));
					student.setStudentNumber(result.getString("f_student_number"));
					studentMap.put(student.getStudentNumber(), student);
				}

				SelectionPo selection = new SelectionPo();
				selection.setStudent(studentMap.get(result.getString("f_student_number")));
				selection.setTeacher(teacherMap.get(result.getString("f_teacher_number")));
				selection.setTopic(topicMap.get(result.getString("f_topic_id")));
				selection.setGrade(result.getString("f_grade"));
				selection.setProcessing(result.getString("f_processing"));
				res.add(selection);
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
	public SelectionPo queryByTopic(TopicPo topic) {
		String sql = "SELECT tb_selection.f_processing , tb_selection.f_grade , tb_topic.f_id AS f_topic_id , "
				+ "tb_topic.f_topic , tb_teacher.f_teacher_number , tb_teacher.f_teacher_name , "
				+ "tb_teacher.f_gender AS f_teacher_gender , tb_teacher.f_title , tb_teacher.f_phone AS f_teacher_phone ,"
				+ " tb_teacher.f_password AS f_teacher_password , tb_student.f_student_number , "
				+ "tb_student.f_student_name , tb_student.f_gender AS f_student_gender , tb_student.f_class AS f_student_class , "
				+ "tb_student.f_phone AS f_student_phone , tb_student.f_password AS f_student_password , "
				+ "tb_department.f_id AS f_teacher_department_id , tb_department.f_department AS f_teacher_department , "
				+ "dept_2.f_id AS f_student_department_id , dept_2.f_department AS f_student_department "
				+ "FROM tb_selection JOIN tb_topic ON tb_topic.f_id = tb_selection.f_topic "
				+ "JOIN tb_teacher ON tb_topic.f_teacher = tb_teacher.f_teacher_number "
				+ "JOIN tb_student ON tb_selection.f_student = tb_student.f_student_number "
				+ "JOIN tb_department ON tb_teacher.f_department = tb_department.f_id "
				+ "JOIN tb_department dept_2 ON tb_student.f_department = dept_2.f_id "
				+ "WHERE tb_topic.f_id = ?; ";
		Connection connection = null;
		List<SelectionPo> res = new ArrayList<SelectionPo>();
		try {
			connection = basicDataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, topic.getId());
			logger.debug(statement);
			ResultSet result = statement.executeQuery();
			Map<String, TopicPo> topicMap = new HashMap<String, TopicPo>();
			Map<String, TeacherPo> teacherMap = new HashMap<String, TeacherPo>();
			Map<String, StudentPo> studentMap = new HashMap<String, StudentPo>();
			Map<String, DepartmentPo> departmentMap = new HashMap<String, DepartmentPo>();
			while (result.next()) {
				if (!departmentMap.containsKey(result.getString("f_teacher_department_id"))) {
					DepartmentPo dept  =  new DepartmentPo();
					dept.setDepartmentId(result.getString("f_teacher_department_id"));
					dept.setDepartmentName(result.getString("f_teacher_department"));
					departmentMap.put(dept.getDepartmentId(), dept);
				}
				
				if (!departmentMap.containsKey(result.getString("f_student_department_id"))) {
					DepartmentPo dept  =  new DepartmentPo();
					dept.setDepartmentId(result.getString("f_student_department_id"));
					dept.setDepartmentName(result.getString("f_student_department_id"));
					departmentMap.put(dept.getDepartmentId(), dept);
				}

				if (!teacherMap.containsKey(result.getString("f_teacher_number"))) {
					TeacherPo teacherPo = new TeacherPo();
					teacherPo.setDepartment(departmentMap.get(result.getString("f_teacher_department_id")));
					teacherPo.setGender(result.getString("f_teacher_gender"));
					teacherPo.setPassword(result.getString("f_teacher_password"));
					teacherPo.setPhone(result.getString("f_teacher_phone"));
					teacherPo.setTeacherName(result.getString("f_teacher_name"));
					teacherPo.setTeacherNumber(result.getString("f_teacher_number"));
					teacherPo.setTitle(result.getString("f_title"));
					teacherMap.put(teacherPo.getTeacherNumber(), teacherPo);
				}
				if (!topicMap.containsKey(result.getString("f_topic_id"))) {
					TopicPo topicPo = new TopicPo();
					topicPo.setId(result.getString("f_topic_id"));
					topicPo.setTeacher(teacherMap.get(result.getString("f_teacher_number")));
					topicPo.setTopic(result.getString("f_topic"));
					topicMap.put(topicPo.getId(), topicPo);
				}
				if (!studentMap.containsKey(result.getString("f_student_number"))) {
					StudentPo student = new StudentPo();
					student.setClassName(result.getString("f_student_class"));
					student.setDepartment(departmentMap.get(result.getString("f_student_department_id")));
					student.setGender(result.getString("f_student_gender"));
					student.setPassword(result.getString("f_student_password"));
					student.setPhone(result.getString("f_student_phone"));
					student.setStudentName(result.getString("f_student_name"));
					student.setStudentNumber(result.getString("f_student_number"));
					studentMap.put(student.getStudentNumber(), student);
				}

				SelectionPo selection = new SelectionPo();
				selection.setStudent(studentMap.get(result.getString("f_student_number")));
				selection.setTeacher(teacherMap.get(result.getString("f_teacher_number")));
				selection.setTopic(topicMap.get(result.getString("f_topic_id")));
				selection.setGrade(result.getString("f_grade"));
				selection.setProcessing(result.getString("f_processing"));
				res.add(selection);
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
		if(res.isEmpty()){
			return null;
		}else{
			return res.get(0);
		}
		
	}

	@Override
	public List<SelectionPo> getAllFinishSelection() {
		List<SelectionPo> res = new ArrayList<SelectionPo>();
		String sql = "SELECT tb_selection.f_processing , tb_selection.f_grade , tb_topic.f_id AS f_topic_id , "
				+ "tb_topic.f_topic , tb_teacher.f_teacher_number , tb_teacher.f_teacher_name , "
				+ "tb_teacher.f_gender AS f_teacher_gender , tb_teacher.f_title , tb_teacher.f_phone AS f_teacher_phone ,"
				+ " tb_teacher.f_password AS f_teacher_password , tb_student.f_student_number , "
				+ "tb_student.f_student_name , tb_student.f_gender AS f_student_gender , tb_student.f_class AS f_student_class , "
				+ "tb_student.f_phone AS f_student_phone , tb_student.f_password AS f_student_password , "
				+ "tb_department.f_id AS f_teacher_department_id , tb_department.f_department AS f_teacher_department , "
				+ "dept_2.f_id AS f_student_department_id , dept_2.f_department AS f_student_department "
				+ "FROM tb_selection JOIN tb_topic ON tb_topic.f_id = tb_selection.f_topic "
				+ "JOIN tb_teacher ON tb_topic.f_teacher = tb_teacher.f_teacher_number "
				+ "JOIN tb_student ON tb_selection.f_student = tb_student.f_student_number "
				+ "JOIN tb_department ON tb_teacher.f_department = tb_department.f_id "
				+ "JOIN tb_department dept_2 ON tb_student.f_department = dept_2.f_id "
				+ "WHERE tb_selection.f_grade IS NOT NULL;";
		Connection connection = null;
		try {
			connection = basicDataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet result = statement.executeQuery();
			Map<String, TopicPo> topicMap = new HashMap<String, TopicPo>();
			Map<String, TeacherPo> teacherMap = new HashMap<String, TeacherPo>();
			Map<String, StudentPo> studentMap = new HashMap<String, StudentPo>();
			Map<String, DepartmentPo> departmentMap = new HashMap<String, DepartmentPo>();
			while (result.next()) {
				if (!departmentMap.containsKey(result.getString("f_teacher_department_id"))) {
					DepartmentPo dept  =  new DepartmentPo();
					dept.setDepartmentId(result.getString("f_teacher_department_id"));
					dept.setDepartmentName(result.getString("f_teacher_department"));
					departmentMap.put(dept.getDepartmentId(), dept);
				}
				
				if (!departmentMap.containsKey(result.getString("f_student_department_id"))) {
					DepartmentPo dept  =  new DepartmentPo();
					dept.setDepartmentId(result.getString("f_student_department_id"));
					dept.setDepartmentName(result.getString("f_student_department_id"));
					departmentMap.put(dept.getDepartmentId(), dept);
				}

				if (!teacherMap.containsKey(result.getString("f_teacher_number"))) {
					TeacherPo teacherPo = new TeacherPo();
					teacherPo.setDepartment(departmentMap.get(result.getString("f_teacher_department_id")));
					teacherPo.setGender(result.getString("f_teacher_gender"));
					teacherPo.setPassword(result.getString("f_teacher_password"));
					teacherPo.setPhone(result.getString("f_teacher_phone"));
					teacherPo.setTeacherName(result.getString("f_teacher_name"));
					teacherPo.setTeacherNumber(result.getString("f_teacher_number"));
					teacherPo.setTitle(result.getString("f_title"));
					teacherMap.put(teacherPo.getTeacherNumber(), teacherPo);
				}
				if (!topicMap.containsKey(result.getString("f_topic_id"))) {
					TopicPo topic = new TopicPo();
					topic.setId(result.getString("f_topic_id"));
					topic.setTeacher(teacherMap.get(result.getString("f_teacher_number")));
					topic.setTopic(result.getString("f_topic"));
					topicMap.put(topic.getId(), topic);
				}
				if (!studentMap.containsKey(result.getString("f_student_number"))) {
					StudentPo student = new StudentPo();
					student.setClassName(result.getString("f_student_class"));
					student.setDepartment(departmentMap.get(result.getString("f_student_department_id")));
					student.setGender(result.getString("f_student_gender"));
					student.setPassword(result.getString("f_student_password"));
					student.setPhone(result.getString("f_student_phone"));
					student.setStudentName(result.getString("f_student_name"));
					student.setStudentNumber(result.getString("f_student_number"));
					studentMap.put(student.getStudentNumber(), student);
				}

				SelectionPo selection = new SelectionPo();
				selection.setStudent(studentMap.get(result.getString("f_student_number")));
				selection.setTeacher(teacherMap.get(result.getString("f_teacher_number")));
				selection.setTopic(topicMap.get(result.getString("f_topic_id")));
				selection.setGrade(result.getString("f_grade"));
				selection.setProcessing(result.getString("f_processing"));
				res.add(selection);
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
