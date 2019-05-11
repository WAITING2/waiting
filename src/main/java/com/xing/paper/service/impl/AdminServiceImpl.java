package com.xing.paper.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xing.paper.dao.AdminDao;
import com.xing.paper.po.AdminPo;
import com.xing.paper.po.NoticePO;
import com.xing.paper.service.AdminService;

@Service
public class AdminServiceImpl  implements AdminService{
	
	@Resource
	AdminDao adminDao;

	@Override
	public AdminPo getAdmin(String username) {
		return adminDao.getAdmin(username);
	}

	@Override
	public boolean addAdmin(AdminPo admin) {
		if(adminDao.getAdmin(admin.getUserName())!=null){
			return false;
		}else{
			adminDao.addAdmin(admin);
			return true;
		}
	}

	@Override
	public void upgradeAdmin(AdminPo admin) {
		adminDao.upgradeAdmin(admin);
	}

	@Override
	public void deleteAdmin(AdminPo admin) {
		adminDao.deleteAdmin(admin);
	}

	@Override
	public boolean comfirmLogin(String username, String password) {
		AdminPo admin = adminDao.getAdmin(username, password);
		return (admin!=null);
	}

	@Override
	public List<AdminPo> getAllAdmin() {
		return adminDao.getAllAdmin();
	}

	@Override
	public AdminPo getAdminById(String id) {
		return adminDao.getAdminById(id);
	}

	@Override
	public List<NoticePO> getAllNotice() {
		return adminDao.getAllNotice();
	}

	@Override
	public void updateNotice(NoticePO notice) {
		adminDao.updateNotice(notice);
	}

	@Override
	public void addNotice(NoticePO notice) {
		adminDao.addNotice(notice);
	}

	@Override
	public NoticePO getNoticeById(Integer id) {
		return adminDao.getNoticeById(id);
	}

	@Override
	public void delNoticeById(Integer id) {
		adminDao.delNoticeById(id);
	}

}
