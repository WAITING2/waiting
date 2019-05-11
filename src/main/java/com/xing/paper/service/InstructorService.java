package com.xing.paper.service;

import java.util.List;

import javax.servlet.ServletOutputStream;

import org.apache.poi.ss.usermodel.Workbook;

import com.xing.paper.po.ClassPO;
import com.xing.paper.po.DepartmentPo;
import com.xing.paper.po.Instructor;
import com.xing.paper.vo.PaperVO;

public interface InstructorService {

	boolean comfirmLogin(String username, String password);

	Instructor queryByInstructorNumber(String username);

	List<ClassPO> findAllClass();

	List<DepartmentPo> findAllDepartment();

	Workbook exportExcle(List<PaperVO> paperVo, String[] titles, ServletOutputStream outputStream);

}
