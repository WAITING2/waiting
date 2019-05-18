package com.xing.paper.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.xing.paper.po.ClassPO;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.xing.paper.dao.StudentDao;
import com.xing.paper.po.DepartmentPo;
import com.xing.paper.po.PaperPO;
import com.xing.paper.po.StudentPo;
import com.xing.paper.vo.PaperVO;

@Repository
public class StudentDaoImpl implements StudentDao {

	private static Logger logger = Logger.getLogger(StudentDaoImpl.class);

	@Resource
	ComboPooledDataSource basicDataSource;

	@Override
	public void addStudent(StudentPo student) {
		String sql = "INSERT INTO tb_student(f_class,f_department,f_gender,f_phone,f_student_name,"
				+ "f_student_number,f_password,f_email) VALUES (?,?,?,?,?,?,?,?);";
		Connection connection = null;
		try {
			connection = basicDataSource.getConnection();
			connection.setAutoCommit(true);
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, student.getClassName());
			statement.setString(2, student.getDepartment().getDepartmentId());
			statement.setString(3, student.getGender());
			statement.setString(4, student.getPhone());
			statement.setString(5, student.getStudentName());
			statement.setString(6, student.getStudentNumber());
			statement.setString(7, student.getPassword());
			statement.setString(8, student.getEmail());
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

	@Override
	public void deleteStudent(StudentPo student) {
		String sql = "DELETE FROM tb_student WHERE f_student_number=?";
		Connection connection = null;
		try {
			connection = basicDataSource.getConnection();
			connection.setAutoCommit(true);
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, student.getStudentNumber());
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

	@Override
	public void updateStudent(StudentPo student) {
		String sql = "UPDATE tb_student SET f_class=?,f_department=?,f_gender=?,f_phone=?,f_student_name=?,"
				+ "f_password=? WHERE f_student_number=?;";
		Connection connection = null;
		try {
			connection = basicDataSource.getConnection();
			connection.setAutoCommit(true);
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, student.getClassName());
			statement.setString(2, student.getDepartment().getDepartmentId());
			statement.setString(3, student.getGender());
			statement.setString(4, student.getPhone());
			statement.setString(5, student.getStudentName());
			statement.setString(6, student.getPassword());
			statement.setString(7, student.getStudentNumber());
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

	@Override
	public List<StudentPo> getAllStudent() {
		List<StudentPo> res = new ArrayList<StudentPo>();
		Map<String, DepartmentPo> departmentMap = new HashMap<String, DepartmentPo>();
		String sql = "SELECT cl.t_id as cid,cl.t_name as cname,f_student_number , f_student_name , f_gender , f_class , f_phone ,f_email, tb_department.f_id AS f_department_id , "
				+ " tb_department.f_department AS f_department_name , f_password FROM tb_student  "
				+ " JOIN tb_department ON tb_department.f_id = tb_student.f_department "
				+ "	LEFT  join tb_class cl on  cl.t_id = tb_student.f_class ";
		Connection connection = null;
		try {
			connection = basicDataSource.getConnection();
			connection.setAutoCommit(true);
			PreparedStatement statement = connection.prepareStatement(sql);
			logger.debug(statement);
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				if (!departmentMap.containsKey(result.getString("f_department_id"))) {
					DepartmentPo dept = new DepartmentPo();
					dept.setDepartmentId(result.getString("f_department_id"));
					dept.setDepartmentName(result.getString("f_department_name"));
					departmentMap.put(dept.getDepartmentId(), dept);
				}
				ClassPO classPO = new ClassPO();
				classPO.setClassName(result.getString("cname"));
				classPO.setId(result.getInt("cid"));
				StudentPo student = new StudentPo();
				student.setDepartment(departmentMap.get(result.getString("f_department_id")));
				student.setClassName(result.getString("f_class"));
				student.setGender(result.getString("f_gender"));
				student.setPassword(result.getString("f_password"));
				student.setPhone(result.getString("f_phone"));
				student.setStudentName(result.getString("f_student_name"));
				student.setStudentNumber(result.getString("f_student_number"));
				student.setEmail(result.getString("f_email"));
				student.setClassPO(classPO);
				res.add(student);
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
	public StudentPo getStudentByNumber(String studentNumber) {
		String sql = "SELECT f_student_number , f_student_name , f_gender , f_class , f_phone ,f_email, tb_department.f_id AS f_department_id , "
				+ "tb_department.f_department AS f_department_name , f_password FROM tb_student "
				+ "JOIN tb_department ON tb_department.f_id = tb_student.f_department WHERE f_student_number=? ;";
		Connection connection = null;
		try {
			connection = basicDataSource.getConnection();
			connection.setAutoCommit(true);
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, studentNumber);
			logger.debug(statement);
			ResultSet result = statement.executeQuery();
			if (result.next()) {
				DepartmentPo dept = new DepartmentPo();
				dept.setDepartmentId(result.getString("f_department_id"));
				dept.setDepartmentName(result.getString("f_department_name"));
				StudentPo student = new StudentPo();
				student.setDepartment(dept);
				student.setClassName(result.getString("f_class"));
				student.setGender(result.getString("f_gender"));
				student.setPassword(result.getString("f_password"));
				student.setPhone(result.getString("f_phone"));
				student.setStudentName(result.getString("f_student_name"));
				student.setStudentNumber(result.getString("f_student_number"));
				student.setEmail(result.getString("f_email"));
				connection.close();
				return student;
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
	public StudentPo queryByPassword(String username, String password) {
		String sql = "SELECT f_student_number , f_student_name , f_gender , f_class , f_phone , tb_department.f_id AS f_department_id , "
				+ "tb_department.f_department AS f_department_name , f_password FROM tb_student "
				+ "JOIN tb_department ON tb_department.f_id = tb_student.f_department WHERE f_student_number=?  AND f_password = ?;";
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
				DepartmentPo dept = new DepartmentPo();
				dept.setDepartmentId(result.getString("f_department_id"));
				dept.setDepartmentName(result.getString("f_department_name"));
				StudentPo student = new StudentPo();
				student.setDepartment(dept);
				student.setClassName(result.getString("f_class"));
				student.setGender(result.getString("f_gender"));
				student.setPassword(result.getString("f_password"));
				student.setPhone(result.getString("f_phone"));
				student.setStudentName(result.getString("f_student_name"));
				student.setStudentNumber(result.getString("f_student_number"));
				connection.close();
				return student;
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
	public void savePaper(PaperPO paper) {
		String sql = "INSERT INTO tb_paper(paper_content,f_teacher_number,f_student_number,paper_status,posted_time," +
				"uu_file_name,file_name) VALUES (?,?,?,?,?,?,?);";
		Connection connection = null;
		try {
			connection = basicDataSource.getConnection();
			connection.setAutoCommit(true);
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, paper.getPaper_content());
			statement.setString(2, paper.getF_teacher_number());
			statement.setString(3, paper.getF_student_number());
			statement.setInt(4, paper.getPaper_status());
			//statement.setDate(5, new Date(paper.getPosted_time().getTime()));
			SimpleDateFormat fmt=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			statement.setString(5,fmt.format(paper.getPosted_time()));
			statement.setString(6, paper.getUu_file_name());
			statement.setString(7, paper.getFile_name());
			int i = statement.executeUpdate();
			logger.info("向论文表插入" + i + "条成功数据");
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Exception", e);
			try {
				connection.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
				logger.error("Exception", e);
			}
		}
	}

	@Override
	public List<PaperPO> getPaperByStudentNumber(String studentNumber, Integer paperId) {
		List<PaperPO> res = new ArrayList<PaperPO>();
		String sql = "select pa.f_student_number,pa.f_teacher_number,pa.id,pa.paper_content,"
				+ " pa.paper_status ,pa.comment ,pa.file_name,pa.uu_file_name,pa.posted_time" + " from tb_paper pa ";

		if (StringUtils.isNoneBlank(studentNumber)) {
			sql += " where pa.f_student_number = ? ";
		}
		if (paperId != null) {
			sql += " where pa.id = ? ";
		}
		Connection connection = null;
		try {
			connection = basicDataSource.getConnection();
			connection.setAutoCommit(true);
			PreparedStatement statement = connection.prepareStatement(sql);
			if (StringUtils.isNoneBlank(studentNumber)) {
				statement.setString(1, studentNumber);
			}
			if (paperId != null) {
				statement.setInt(1, paperId);
			}
			logger.debug(statement);
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				PaperPO po = new PaperPO();
				po.setF_student_number(result.getString("f_student_number"));
				po.setF_teacher_number(result.getString("f_teacher_number"));
				po.setId(result.getInt("id"));
				po.setPaper_content(result.getString("paper_content"));
				po.setPaper_status(result.getInt("paper_status"));
				po.setComment(result.getString("comment"));
				po.setUu_file_name(result.getString("uu_file_name"));
				po.setFile_name(result.getString("file_name"));
				po.setPosted_time(result.getDate("posted_time"));
				res.add(po);
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
		logger.info("获取到" + res.size() + "条论文信息");
		return res;
	}

	@Override
	public void updatePaper(PaperPO po) {
		String sql = "UPDATE tb_paper SET paper_content=?,f_teacher_number=?,f_student_number=?,paper_status=?,comment=?  WHERE id=?;";
		Connection connection = null;
		try {
			connection = basicDataSource.getConnection();
			connection.setAutoCommit(true);
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, po.getPaper_content());
			statement.setString(2, po.getF_teacher_number());
			statement.setString(3, po.getF_student_number());
			statement.setInt(4, po.getPaper_status());
			statement.setString(5, po.getComment());
			statement.setInt(6, po.getId());
			logger.debug(statement);
			int i = statement.executeUpdate();
			logger.info("成功的修改" + i + "条论文信息");
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
	public List<PaperPO> getFailPaperByStudentNumber(String studentNumber) {
		List<PaperPO> res = new ArrayList<PaperPO>();
		String sql = "select pa.f_student_number,pa.f_teacher_number,pa.id,pa.paper_content,"
				+ " pa.paper_status ,pa.comment "
				+ " from tb_paper pa where pa.f_student_number = ? and  (paper_status = 0 or paper_status = 2) ";
		Connection connection = null;
		try {
			connection = basicDataSource.getConnection();
			connection.setAutoCommit(true);
			PreparedStatement statement = connection.prepareStatement(sql);
			if (StringUtils.isNoneBlank(studentNumber)) {
				statement.setString(1, studentNumber);
			}
			logger.debug(statement);
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				PaperPO po = new PaperPO();
				po.setF_student_number(result.getString("f_student_number"));
				po.setF_teacher_number(result.getString("f_teacher_number"));
				po.setId(result.getInt("id"));
				po.setPaper_content(result.getString("paper_content"));
				po.setPaper_status(result.getInt("paper_status"));
				po.setComment(result.getString("comment"));
				res.add(po);
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
		logger.info("获取到" + res.size() + "条论文信息");
		return res;
	}

	@Override
	public List<PaperVO> getAllPaperInfoByStudentName(String studentName, String teacherName, String departId, Integer classId) {

		List<PaperVO> res = new ArrayList<PaperVO>();

		String sql = " select pa.id , pa.f_student_number ,top.f_topic,st.f_student_name,dep.f_department,cl.t_name,te.f_teacher_name,pa.paper_status,pa.posted_time  from tb_paper pa "+

				" left join tb_teacher te on pa.f_teacher_number = te.f_teacher_number "+

				" left join tb_student st on pa.f_student_number = st.f_student_number "+

				" left join tb_selection se on st.f_student_number = se.f_student "+

				" left join tb_department dep on dep.f_id = st.f_department "+

				" left join tb_class cl on cl.t_id = st.f_class "+

				" left join tb_topic top on top.f_id = se.f_topic  " +

				" where (pa.f_student_number,pa.posted_time) in ( " +

				" select p.f_student_number ,max(p.posted_time) from tb_paper p " +

				" group by p.f_student_number " +

				" ) ";

		int i = 0;

		if(StringUtils.isNoneBlank(studentName)){

			sql += " and st.f_student_name like ? ";

			i++;

		}

		if(StringUtils.isNoneBlank(teacherName)){

			sql += " and te.f_teacher_name like ? ";

			i++;

		}

		if(StringUtils.isNoneBlank(departId)){

			sql += " and dep.f_id = ? ";

			i++;

		}

		if(classId != null){

			sql += " and cl.t_id = ? ";

			i++;

		}

//		sql += " order by st.f_student_name ";

//		sql += " group by pa.f_student_number ";

		Connection connection = null;
		try {

			connection = basicDataSource.getConnection();

			connection.setAutoCommit(true);

			PreparedStatement statement = connection.prepareStatement(sql);

			if(classId != null){

				statement.setInt(i,classId);

				i--;

			}

			if(StringUtils.isNoneBlank(departId)){

				statement.setString(i,departId);

				i--;
			}

			if(StringUtils.isNoneBlank(teacherName)){

				statement.setString(i,"%"+teacherName+"%");

				i--;

			}

			if(StringUtils.isNoneBlank(studentName)){

				statement.setString(i, "%"+studentName+"%");

			}

			logger.debug(statement);

			ResultSet result = statement.executeQuery();

			while (result.next()) {

				PaperVO paperVO = new PaperVO();

				paperVO.setId(result.getInt("id"));

				paperVO.setPaperStatus(result.getInt("paper_status"));

				paperVO.setStudentName(result.getString("f_student_name"));

				paperVO.setTeacherName(result.getString("f_teacher_name"));

				paperVO.setTopic(result.getString("f_topic"));

				paperVO.setDepartment(result.getString("f_department"));

				paperVO.setClassName(result.getString("t_name"));

//				paperVO.setTeacherName(result.getString("teacherName"));

				paperVO.setPosted_time(result.getDate("posted_time"));

				res.add(paperVO);

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
