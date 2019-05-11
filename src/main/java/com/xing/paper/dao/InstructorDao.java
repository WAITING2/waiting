package com.xing.paper.dao;

import java.util.List;

import com.xing.paper.po.ClassPO;
import com.xing.paper.po.DepartmentPo;
import com.xing.paper.po.Instructor;

public interface InstructorDao {

	List<Instructor> getInstrucorByUsernameAndPassword(String username, String password);

	Instructor queryByInstructorNumber(String username);

	List<ClassPO> findAllClass();

	List<DepartmentPo> findAllDepartment();

}
