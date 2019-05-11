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
import com.xing.paper.dao.DepartmentDao;
import com.xing.paper.po.DepartmentPo;

@Repository
public class DepartmentDaoImpl implements DepartmentDao {

	private static Logger logger = Logger.getLogger(DepartmentDaoImpl.class);

	@Resource
	ComboPooledDataSource basicDataSource;

	@Override
	public List<DepartmentPo> getAllDepartment() {
		String sql = "SELECT * FROM tb_department;";
		List<DepartmentPo> res = new ArrayList<DepartmentPo>();
		Connection connection = null;
		try {
			connection = basicDataSource.getConnection();
			connection.setAutoCommit(true);
			PreparedStatement statement = connection.prepareStatement(sql);
			logger.debug(statement);
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				DepartmentPo dept = new DepartmentPo();
				dept.setDepartmentId(result.getString("f_id"));
				dept.setDepartmentName(result.getString("f_department"));
				res.add(dept);
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
	public DepartmentPo getDepartmentById(String deptId) {
		String sql = "SELECT * FROM tb_department WHERE f_id=?;";
		Connection connection = null;
		try {
			connection = basicDataSource.getConnection();
			connection.setAutoCommit(true);
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, deptId);
			logger.debug(statement);
			ResultSet result = statement.executeQuery();
			if (result.next()) {
				DepartmentPo dept = new DepartmentPo();
				dept.setDepartmentId(result.getString("f_id"));
				dept.setDepartmentName(result.getString("f_department"));
				connection.close();
				return dept;
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

}
