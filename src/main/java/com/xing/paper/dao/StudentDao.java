package com.xing.paper.dao;

import java.util.List;

import com.xing.paper.po.PaperPO;
import com.xing.paper.po.StudentPo;
import com.xing.paper.vo.PaperVO;

 public interface StudentDao {
	
	 void addStudent(StudentPo student);
	 void deleteStudent(StudentPo student);
	 void updateStudent(StudentPo student);
	 List<StudentPo> getAllStudent();
	 StudentPo getStudentByNumber(String studentNumber);
	 StudentPo queryByPassword(String username,String password);
	 void savePaper(PaperPO paper);
	 List<PaperPO> getPaperByStudentNumber(String studentNumber, Integer paperId);
	 void updatePaper(PaperPO po);
	 List<PaperPO> getFailPaperByStudentNumber(String studentNumber);
	 List<PaperVO> getAllPaperInfoByStudentName(String studentName, String teacherName, String departId, Integer classId);

	 PaperPO getPaperById(Integer paper_id);

	 void updatePaper2(PaperPO paper);
 }
