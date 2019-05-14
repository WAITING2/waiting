package com.xing.paper.dao;

import java.util.List;

import com.xing.paper.po.DepartmentPo;

public interface DepartmentDao {
	
	public List<DepartmentPo> getAllDepartment();
	
	public DepartmentPo getDepartmentById(String deptId);

}
