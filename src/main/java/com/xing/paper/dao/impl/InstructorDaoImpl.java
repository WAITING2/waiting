package com.xing.paper.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.xing.paper.dao.InstructorDao;
import com.xing.paper.po.ClassPO;
import com.xing.paper.po.DepartmentPo;
import com.xing.paper.po.Instructor;

@Repository
public class InstructorDaoImpl implements InstructorDao{
	private static Logger logger = Logger.getLogger(InstructorDaoImpl.class);
	@Resource
	ComboPooledDataSource basicDataSource;
	@Override
	public List<Instructor> getInstrucorByUsernameAndPassword(String username, String password) {
		List<Instructor> res = new ArrayList<Instructor>();
		String sql = "SELECT * from tb_instructor where f_instructor_number=? and f_password = ?";
		Connection connection = null;
		try {
			connection = basicDataSource.getConnection();
			connection.setAutoCommit(true);
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, username);
			statement.setString(2, password);
			logger.debug(statement);
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				Instructor instructor = new Instructor();
				instructor.setGender(result.getString("f_gender"));
				instructor.setInstructorName(result.getString("f_instructor_name"));
				instructor.setInstructorNumber(result.getString("f_instructor_number"));
				instructor.setPassword(result.getString("f_password"));
				res.add(instructor);
			}
			connection.close();
		} catch (SQLException e) {
			logger.error("Exception", e);
			try {
				connection.close();
			} catch (SQLException e1) {
				logger.error("Exception", e);
			}
			return null;
		}
		return res;
	}
	@Override
	public Instructor queryByInstructorNumber(String username) {
		List<Instructor> res = new ArrayList<Instructor>();
		String sql = "SELECT * from tb_instructor where f_instructor_number=? ";
		Connection connection = null;
		try {
			connection = basicDataSource.getConnection();
			connection.setAutoCommit(true);
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, username);
			logger.debug(statement);
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				Instructor instructor = new Instructor();
				instructor.setGender(result.getString("f_gender"));
				instructor.setInstructorName(result.getString("f_instructor_name"));
				instructor.setInstructorNumber(result.getString("f_instructor_number"));
				instructor.setPassword(result.getString("f_password"));
				res.add(instructor);
			}
			connection.close();
			if(res.size()<1){
				logger.info("没查询到辅导员信息...");
				return null;
			}
		} catch (SQLException e) {
			logger.error("Exception", e);
			try {
				connection.close();
			} catch (SQLException e1) {
				logger.error("Exception", e);
			}
			return null;
		}
		return res.get(0);
	}
	@Override
	public List<ClassPO> findAllClass() {
		List<ClassPO> res = new ArrayList<ClassPO>();
		String sql = " SELECT * from tb_class  ";
		Connection connection = null;
		try {
			connection = basicDataSource.getConnection();
			connection.setAutoCommit(true);
			PreparedStatement statement = connection.prepareStatement(sql);
			logger.debug(statement);
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				ClassPO po = new ClassPO();
				po.setClassName(result.getString("t_name"));
				po.setId(result.getInt("t_id"));
				res.add(po);
			}
			connection.close();
			if(res.size()<1){
				logger.info("没查询到班级信息信息...");
				return null;
			}
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
	public List<DepartmentPo> findAllDepartment() {
		List<DepartmentPo> res = new ArrayList<DepartmentPo>();
		String sql = " SELECT * from tb_department  ";
		Connection connection = null;
		try {
			connection = basicDataSource.getConnection();
			connection.setAutoCommit(true);
			PreparedStatement statement = connection.prepareStatement(sql);
			logger.debug(statement);
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				DepartmentPo po = new DepartmentPo();
				po.setDepartmentId(result.getString("f_id"));
				po.setDepartmentName(result.getString("f_department"));
				res.add(po);
			}
			connection.close();
			if(res.size()<1){
				logger.info("没查询到院系信息...");
				return null;
			}
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
