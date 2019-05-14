package com.xing.paper.po;

public class ClassPO {
	private Integer id;
	private String className;
	
	public ClassPO() {
		super();
	}
	
	public ClassPO(Integer id, String className) {
		super();
		this.id = id;
		this.className = className;
	}


	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	
	
}
