package com.xing.paper.dao;

import java.util.List;

import com.xing.paper.po.AdminPo;
import com.xing.paper.po.NoticePO;

public interface AdminDao {
	
	public void addAdmin(AdminPo admin);
	public void upgradeAdmin(AdminPo admin);
	public void deleteAdmin(AdminPo admin);
	public AdminPo getAdmin(String username);
	public AdminPo getAdmin(String username,String password);
	public List<AdminPo> getAllAdmin();
	public AdminPo getAdminById(String id);
	public List<NoticePO> getAllNotice();
	public void updateNotice(NoticePO notice);
	public void addNotice(NoticePO notice);
	public NoticePO getNoticeById(Integer id);
	public void delNoticeById(Integer id);

}
