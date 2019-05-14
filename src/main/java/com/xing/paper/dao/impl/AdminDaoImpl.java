package com.xing.paper.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.xing.paper.dao.AdminDao;
import com.xing.paper.po.AdminPo;
import com.xing.paper.po.NoticePO;

@Repository
public class AdminDaoImpl implements AdminDao {

	private static Logger logger = Logger.getLogger(AdminDaoImpl.class);

	@Resource
	ComboPooledDataSource basicDataSource;

	@Override
	public AdminPo getAdmin(String username, String password) {
		String sql = "SELECT f_password,f_username,f_id FROM tb_administrator WHERE f_username = ? AND f_password=?;";
		Connection connection = null;
		try {
			connection = basicDataSource.getConnection();
			connection.setAutoCommit(true);
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, username);
			statement.setString(2, password);
			logger.debug(statement);
			ResultSet result = statement.executeQuery();
			if (result.next()) {
				AdminPo admin = new AdminPo();
				admin.setPassword(result.getString("f_password"));
				admin.setUserName(result.getString("f_username"));
				admin.setId(result.getString("f_id"));
				connection.close();
				return admin;
			} else {
				connection.close();
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
	}

	@Override
	public void addAdmin(AdminPo admin) {
		String sql = "INSERT INTO tb_administrator(f_password,f_username) VALUES(?,?);";
		Connection connection = null;
		try {
			connection = basicDataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, admin.getPassword());
			statement.setString(2, admin.getUserName());
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
	public void upgradeAdmin(AdminPo admin) {
		String sql = "UPDATE tb_administrator SET f_password = ?,f_username=? WHERE f_id = ?;";
		Connection connection = null;
		try {
			connection = basicDataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, admin.getPassword());
			statement.setString(2, admin.getUserName());
			statement.setString(3, admin.getId());
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
	public void deleteAdmin(AdminPo admin) {
		String sql = "DELETE FROM tb_administrator WHERE f_id = ?";
		Connection connection = null;
		try {
			connection = basicDataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, admin.getId());
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
	public AdminPo getAdmin(String username) {
		String sql = "SELECT f_password,f_username,f_id FROM tb_administrator WHERE f_username = ?;";
		Connection connection = null;
		try {
			connection = basicDataSource.getConnection();
			connection.setAutoCommit(true);
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, username);
			logger.debug(statement);
			ResultSet result = statement.executeQuery();
			if (result.next()) {
				AdminPo admin = new AdminPo();
				admin.setPassword(result.getString("f_password"));
				admin.setUserName(result.getString("f_username"));
				admin.setId(result.getString("f_id"));
				connection.close();
				return admin;
			} else {
				connection.close();
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
	}

	@Override
	public List<AdminPo> getAllAdmin() {
		String sql = "SELECT f_id,f_password,f_username,f_id FROM tb_administrator;";
		List<AdminPo> res = new ArrayList<AdminPo>();
		Connection connection = null;
		try {
			connection = basicDataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			logger.debug(statement);
			ResultSet result = statement.executeQuery();
			while(result.next()){
				AdminPo admin = new AdminPo();
				admin.setId(result.getString("f_id"));
				admin.setPassword(result.getString("f_password"));
				admin.setUserName(result.getString("f_username"));
				res.add(admin);
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
	public AdminPo getAdminById(String id) {
		String sql = "SELECT f_password,f_username,f_id FROM tb_administrator WHERE f_id = ?;";
		Connection connection = null;
		try {
			connection = basicDataSource.getConnection();
			connection.setAutoCommit(true);
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, id);
			logger.debug(statement);
			ResultSet result = statement.executeQuery();
			if (result.next()) {
				AdminPo admin = new AdminPo();
				admin.setPassword(result.getString("f_password"));
				admin.setUserName(result.getString("f_username"));
				admin.setId(result.getString("f_id"));
				connection.close();
				return admin;
			} else {
				connection.close();
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
	}

	@Override
	public List<NoticePO> getAllNotice() {
		String sql = " select ce.admin_id,ce.content,ce.createdate,ce.id,ce.title,ad.f_id,ad.f_username "+

					 " from tb_notice ce "+

					 " left join tb_administrator ad on ad.f_id = ce.admin_id ";
		List<NoticePO> res = new ArrayList<NoticePO>();
		Connection connection = null;
		try {
			connection = basicDataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			logger.debug(statement);
			ResultSet result = statement.executeQuery();
			while(result.next()){
				AdminPo admin = new AdminPo();
				admin.setId(result.getString("f_id"));
				admin.setUserName(result.getString("f_username"));
				NoticePO po = new NoticePO();
				po.setAdmin(admin);
				po.setContent(result.getString("content"));
				po.setId(result.getInt("id"));
				po.setCreatedate(result.getDate("createdate"));
				po.setTitle(result.getString("title"));
				res.add(po);
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
	public void updateNotice(NoticePO notice) {
		String sql = "UPDATE tb_notice SET title = ?,content = ? WHERE id = ?;";
		Connection connection = null;
		try {
			connection = basicDataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, notice.getTitle());
			statement.setString(2, notice.getContent());
			statement.setInt(3, notice.getId());
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
	public void addNotice(NoticePO notice) {
		String sql = "INSERT INTO tb_notice (title,content,createdate,admin_id) VALUES(?,?,?,?);";
		Connection connection = null;
		try {
			connection = basicDataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, notice.getTitle());
			statement.setString(2, notice.getContent());
			statement.setDate(3,new Date( notice.getCreatedate().getTime()));
			statement.setString(4, notice.getAdmin_id());
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
	public NoticePO getNoticeById(Integer id) {
		String sql = " select ce.admin_id,ce.content,ce.createdate,ce.id,ce.title,ad.f_id,ad.f_username "+

					 " from tb_notice ce "+

					 " left join tb_administrator ad on ad.f_id = ce.admin_id  where ce.id = ? ";
		Connection connection = null;
		NoticePO po = new NoticePO();
		try {
			connection = basicDataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, id);
			logger.debug(statement);
			ResultSet result = statement.executeQuery();
			if(result.next()){
				AdminPo admin = new AdminPo();
				admin.setId(result.getString("f_id"));
				admin.setUserName(result.getString("f_username"));
				po.setAdmin(admin);
				po.setContent(result.getString("content"));
				po.setId(result.getInt("id"));
				po.setCreatedate(result.getDate("createdate"));
				po.setTitle(result.getString("title"));
			}
		} catch (SQLException e) {
			logger.error("Exception", e);
			try {
				connection.close();
			} catch (SQLException e1) {
				logger.error("Exception", e);
			}
		}
		return po;
	}

	@Override
	public void delNoticeById(Integer id) {
		String sql = "DELETE FROM tb_notice WHERE id=?";
		Connection connection = null;
		try {
			connection = basicDataSource.getConnection();
			connection.setAutoCommit(true);
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, id);
			logger.debug(statement);
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

}
