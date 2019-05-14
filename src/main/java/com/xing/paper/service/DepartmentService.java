package com.xing.paper.service;

import java.util.List;

import com.xing.paper.po.DepartmentPo;

public interface DepartmentService {
	
	public List<DepartmentPo> getAllDepartment();
	
	public DepartmentPo getDepartmentById(String deptId);

}
