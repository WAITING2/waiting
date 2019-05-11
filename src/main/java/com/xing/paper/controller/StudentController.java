package com.xing.paper.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xing.paper.po.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xing.paper.service.AdminService;
import com.xing.paper.service.DepartmentService;
import com.xing.paper.service.StudentService;
import com.xing.paper.service.TeacherService;
import com.xing.paper.service.TopicService;
import com.xing.paper.service.impl.SelectionServiceImpl;

@Controller
public class StudentController {
	
	@Resource
	DepartmentService departmentService;
	@Resource
	SelectionServiceImpl selectionService;
	@Resource
	StudentService studentService;
	@Resource
	TeacherService teacherService;
	@Resource
	TopicService topicService;
	@Resource
	AdminService adminService;
	
	@RequestMapping(value="/student/index")
	public String getStudentIndex(HttpServletRequest request,HttpServletResponse reponse){
		String studentNumber = (String) request.getSession().getAttribute("studentNumber");
		StudentPo student = studentService.getStudentByNumber(studentNumber);
		if(student!=null){
			request.setAttribute("selectionInfo", selectionService.queryByStudent(student));
		}
		return "student/student-index";
	}
	
	@RequestMapping(value="/student/select")
	public String getStudentSelectPage(HttpServletRequest request,HttpServletResponse reponse){
		String studentNumber = (String) request.getSession().getAttribute("studentNumber");
		StudentPo student = studentService.getStudentByNumber(studentNumber);
		SelectionPo selection = selectionService.queryByStudent(student);
		if(student!=null){
			request.setAttribute("selectionInfo",selection);
		}
		if(selection==null){
			request.setAttribute("topics", topicService.getNotSelectedTopic());
		}
		return "student/student-select";
	}
	
	@RequestMapping(value="/student/info")
	public String getStudentInfo(HttpServletRequest request,HttpServletResponse reponse){
		String studentNumber = (String) request.getSession().getAttribute("studentNumber");
		request.setAttribute("student", studentService.getStudentByNumber(studentNumber));
		request.setAttribute("departments", departmentService.getAllDepartment());
		return "student/student-info";
	}
	
	@RequestMapping(value="/student/info/modify")
	public void doStudentModify(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String studentNumber = (String) request.getParameter("studentNumber");
		String deptId = request.getParameter("department");
		DepartmentPo dept = null;
		StudentPo student = null;
		if (deptId != null) {
			dept = departmentService.getDepartmentById(deptId);
		}
		if (studentNumber != null) {
			student = studentService.getStudentByNumber(studentNumber);
		}
		if (student != null && dept != null) {
			student.setClassName(request.getParameter("class"));
			student.setDepartment(dept);
			student.setGender(request.getParameter("gender"));
			if(request.getParameter("password")!=null && request.getParameter("password").length()>0 ){
				student.setPassword(request.getParameter("password"));
			}
			student.setPhone(request.getParameter("phone"));
			student.setStudentName(request.getParameter("name"));
			studentService.updateStudent(student);
		}
		response.sendRedirect(request.getContextPath()+"/student/info");
	}
	
	@RequestMapping(value="/student/select/handle")
	@ResponseBody
	public Map<String,Object> doSelectionUpdate(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> res = new HashMap<String,Object>();
		String studentNumber = (String) request.getSession().getAttribute("studentNumber");
		if(studentNumber!=null){
			StudentPo student = studentService.getStudentByNumber(studentNumber);
			TopicPo topic = topicService.queryByTopicId(request.getParameter("topic"));
			if(student!=null  && topic!=null){
				SelectionPo selection = new SelectionPo();
				selection.setGrade(null);
				selection.setProcessing("选题通过");
				selection.setStudent(student);
				selection.setTeacher(topic.getTeacher());
				selection.setTopic(topic);
				boolean isAdd = selectionService.addSelection(selection);
				if(isAdd){
					res.put("status", "ok");
				}else{
					res.put("status", "error");
				}
			}else{
				res.put("status", "error");
			}
		}else{
			res.put("status", "error");
		}
		return res;
	}
	
	@RequestMapping(value="/student/paper")
	public String getStudentPaper(HttpServletRequest request,HttpServletResponse reponse){
		String studentNumber = (String) request.getSession().getAttribute("studentNumber");
		StudentPo po = studentService.getStudentByNumber(studentNumber);
		List<PaperPO> pos = studentService.getPaperByStudentNumber(studentNumber,null);
		Integer t_flag = 1;//用来标识学生是否还可以上传论文
		for (PaperPO paperPO : pos) {
			if(paperPO.getPaper_status().equals(2)){
				t_flag = 2;
				break;
			}
			if(paperPO.getPaper_status().equals(0)){
				t_flag = 0 ;
				break;
			}
		}


		SelectionPo selection = selectionService.queryByStudent(po);
		//要先进行选题才会才能上传论文
		if(selection == null){
			request.setAttribute("teacher", new TeacherPo());
			t_flag = 3;//表示没选题的flag
		}else{
			request.setAttribute("teacher",selection.getTeacher());
		}
		po.setPaper_flag(t_flag);
		request.setAttribute("student", po);
		request.setAttribute("papers", pos);
		return "student/student-paper";
	}
	
	@RequestMapping(value = "/student/notice")
	public String getNoticePage(HttpServletRequest request, HttpServletResponse response) {
		List<NoticePO> notices = adminService.getAllNotice();
		request.setAttribute("notices", notices);
		return "student/notice-index";
	}

}
