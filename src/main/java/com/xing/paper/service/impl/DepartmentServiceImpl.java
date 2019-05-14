package com.xing.paper.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xing.paper.dao.DepartmentDao;
import com.xing.paper.po.DepartmentPo;
import com.xing.paper.service.DepartmentService;

@Service
public class DepartmentServiceImpl implements DepartmentService{
	
	@Resource
	DepartmentDao departmentDao;

	@Override
	public List<DepartmentPo> getAllDepartment() {
		return departmentDao.getAllDepartment();
	}

	@Override
	public DepartmentPo getDepartmentById(String deptId) {
		return departmentDao.getDepartmentById(deptId);
	}

}
