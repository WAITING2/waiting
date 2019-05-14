package com.xing.paper.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.mail.internet.AddressException;

import com.xing.paper.utils.DoSend;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.xing.paper.dao.StudentDao;
import com.xing.paper.po.PaperPO;
import com.xing.paper.po.StudentPo;
import com.xing.paper.service.StudentService;
import com.xing.paper.vo.MailEntry;
import com.xing.paper.vo.PaperVO;

@Service
public class StudentServiceImpl implements StudentService{
	private static Logger log = Logger.getLogger(StudentServiceImpl.class);
	@Resource
	private StudentDao studentDao;

	@Override
	public List<StudentPo> getAllStudent() {
		return studentDao.getAllStudent();
	}

	@Override
	public StudentPo getStudentByNumber(String studentNumber) {
		return studentDao.getStudentByNumber(studentNumber);
	}

	@Override
	public void addStudent(StudentPo student) {
		studentDao.addStudent(student);
	}

	@Override
	public void deleteStudent(StudentPo student) {
		studentDao.deleteStudent(student);
	}

	@Override
	public void updateStudent(StudentPo student) {
		studentDao.updateStudent(student);
	}

	@Override
	public boolean comfirmLogin(String username, String password) {
		StudentPo student = studentDao.queryByPassword(username, password);
		if(student != null){
			return true;
		}
		return false;
	}

	@Override
	public void savePaper(PaperPO paper) {
		studentDao.savePaper(paper);
	}

	@Override
	public List<PaperPO> getPaperByStudentNumber(String studentNumber,Integer paperId) {
		return studentDao.getPaperByStudentNumber(studentNumber,paperId);
	}

	@Override
	public void updatePaper(PaperPO po,StudentPo studentPo) {
		studentDao.updatePaper(po);//修改成功发送短信
//		sendSms("13147123193","同学，您的论文没通过。请及时修改",);
		MailEntry entry = new MailEntry();
		try {
			entry.setCarbonCopy(studentPo.getEmail());
			entry.setRecipients(studentPo.getEmail());
			entry.setSubject("论文结果");
			entry.setText(studentPo.getStudentName()+"同学  你好:<br>    论文的结果为"+(po.getPaper_status() == 0 ? "通过" : "未通过"));
			DoSend.sendMail(studentPo.getEmail(), studentPo.getStudentName()+"同学  你好:<br>    论文"+(po.getPaper_status() == 0 ? "通过" : "未通过"), "论文审核结果");
		} catch (AddressException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<PaperPO> getFailPaperByStudentNumber(String studentNumber) {
		return studentDao.getFailPaperByStudentNumber(studentNumber);
	}

	@Override
	public List<PaperVO> getAllPaperInfoByStudentName(String studentName, String teacherName, String departId, Integer classId) {
		return studentDao.getAllPaperInfoByStudentName(studentName,teacherName,departId,classId);
	}
	
}
