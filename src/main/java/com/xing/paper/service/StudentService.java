package com.xing.paper.service;

import java.io.File;
import java.util.List;

import com.xing.paper.po.PaperPO;
import com.xing.paper.po.StudentPo;
import com.xing.paper.vo.PaperVO;

public interface StudentService {
	
	public void addStudent(StudentPo student);
	public void deleteStudent(StudentPo student);
	public void updateStudent(StudentPo student);
	public List<StudentPo> getAllStudent();
	public StudentPo getStudentByNumber(String studentNumber);
	public boolean comfirmLogin(String username,String password);
	public void savePaper(PaperPO paper, File file)throws Exception;
	public List<PaperPO> getPaperByStudentNumber(String studentNumber, Integer paperId);
	public void updatePaper(PaperPO po, StudentPo studentPo);
	public List<PaperPO> getFailPaperByStudentNumber(String studentNumber);
	public List<PaperVO> getAllPaperInfoByStudentName(String studentName, String teacherName, String departId, Integer classId);
}
