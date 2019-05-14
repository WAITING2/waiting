package com.xing.paper.po;

import java.util.Date;

public class NoticePO {
	private Integer id;
	private String title;
	private String content;
	private Date createdate;
	private String admin_id;
	private AdminPo admin;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getCreatedate() {
		return createdate;
	}
	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}
	public String getAdmin_id() {
		return admin_id;
	}
	public void setAdmin_id(String admin_id) {
		this.admin_id = admin_id;
	}
	public AdminPo getAdmin() {
		return admin;
	}
	public void setAdmin(AdminPo admin) {
		this.admin = admin;
	}
	
	
}
