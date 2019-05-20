package com.xing.paper.service;

import java.io.File;
import java.util.List;

import com.xing.paper.po.PaperPO;
import com.xing.paper.po.StudentPo;
import com.xing.paper.vo.PaperVO;

public interface StudentService {
	
	void addStudent(StudentPo student);
	void deleteStudent(StudentPo student);
	void updateStudent(StudentPo student);
	List<StudentPo> getAllStudent();
	StudentPo getStudentByNumber(String studentNumber);
	boolean comfirmLogin(String username,String password);
	void savePaper(PaperPO paper, File file)throws Exception;
	List<PaperPO> getPaperByStudentNumber(String studentNumber, Integer paperId);
	void updatePaper(PaperPO po, StudentPo studentPo);
	List<PaperPO> getFailPaperByStudentNumber(String studentNumber);
	List<PaperVO> getAllPaperInfoByStudentName(String studentName, String teacherName, String departId, Integer classId);

    PaperPO getPaperById(Integer paper_id);
}
