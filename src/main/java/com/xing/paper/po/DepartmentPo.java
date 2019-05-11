package com.xing.paper.po;

public class DepartmentPo {

	private String departmentId;
	private String departmentName;

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	@Override
	public String toString() {
		return "DepartmentPo [departmentId=" + departmentId + ", departmentName=" + departmentName + "]";
	}

}
