package com.xing.paper.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xing.paper.po.ClassPO;
import com.xing.paper.po.DepartmentPo;
import com.xing.paper.po.Instructor;
import com.xing.paper.po.NoticePO;
import com.xing.paper.service.AdminService;
import com.xing.paper.service.DepartmentService;
import com.xing.paper.service.InstructorService;
import com.xing.paper.service.StudentService;
import com.xing.paper.service.TeacherService;
import com.xing.paper.service.TopicService;
import com.xing.paper.service.impl.SelectionServiceImpl;
import com.xing.paper.utils.DateUtils;
import com.xing.paper.vo.PaperVO;

@Controller
public class InstructorController {
	
	@Resource
	InstructorService instructorService;
	@Resource
	DepartmentService departmentService;
	@Resource
	SelectionServiceImpl selectionService;
	@Resource
	StudentService studentService;
	@Resource
	TeacherService teacherService;
	@Resource
	AdminService adminService;
	@Resource
	TopicService topicService;
	
	@RequestMapping(value="/instructor/index")
	public String getStudentIndex(HttpServletRequest request,HttpServletResponse reponse,
			String studentUsername,String teacherName,String departId,Integer classId){
		String instructorNumber =  (String)request.getSession().getAttribute("instructorNumber");
		Instructor instructor = instructorService.queryByInstructorNumber(instructorNumber);
		List<ClassPO> classes = instructorService.findAllClass();//查询出所有class信息
		List<DepartmentPo> departs = instructorService.findAllDepartment();//查询所有的院系
		List<PaperVO> paperVo = studentService.getAllPaperInfoByStudentName(studentUsername,teacherName, departId,classId);
		request.setAttribute("instructor",instructor);
		request.setAttribute("studentUsername",studentUsername);
		request.setAttribute("papers",paperVo);
		request.setAttribute("classes",classes);
		request.setAttribute("departs",departs);
		request.setAttribute("teacherName",teacherName);
		request.setAttribute("departId",departId);
		request.setAttribute("classId",classId);
		return "instructor/instructor-index";
	}
	
	@RequestMapping(value="/instructor/exportExcle")
	public String exportExcle(HttpServletRequest request,HttpServletResponse response,String studentUsername,String teacherName,String departId,Integer classId) throws IOException{
		ServletOutputStream outputStream = response.getOutputStream();
		List<PaperVO> paperVo = studentService.getAllPaperInfoByStudentName(studentUsername,teacherName, departId,classId);
		String fileName ="paperList"+"-"+DateUtils.formatDateTime(new Date());
		response.setHeader("Content-disposition", "attachment; filename="
				+ fileName + ".xlsx");// 组装附件名称和格式
		String[] titles = { "选题","	学生","专业", "班级","老师",	"论文状态"};
		Workbook workBook=instructorService.exportExcle(paperVo, titles, outputStream);
		workBook.write(outputStream);
		outputStream.flush();
		outputStream.close();
		return null;
	}
	
	@RequestMapping(value = "/instructor/notice")
	public String getNoticePage(HttpServletRequest request, HttpServletResponse response) {
		List<NoticePO> notices = adminService.getAllNotice();
		request.setAttribute("notices", notices);
		return "instructor/notice-index";
	}

}
