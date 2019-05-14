package com.xing.paper.dao;

import java.util.List;

import com.xing.paper.po.PaperPO;
import com.xing.paper.po.StudentPo;
import com.xing.paper.vo.PaperVO;

public interface StudentDao {
	
	public void addStudent(StudentPo student);
	public void deleteStudent(StudentPo student);
	public void updateStudent(StudentPo student);
	public List<StudentPo> getAllStudent();
	public StudentPo getStudentByNumber(String studentNumber);
	public StudentPo queryByPassword(String username,String password);
	public void savePaper(PaperPO paper);
	public List<PaperPO> getPaperByStudentNumber(String studentNumber, Integer paperId);
	public void updatePaper(PaperPO po);
	public List<PaperPO> getFailPaperByStudentNumber(String studentNumber);
	public List<PaperVO> getAllPaperInfoByStudentName(String studentName, String teacherName, String departId, Integer classId);

}
