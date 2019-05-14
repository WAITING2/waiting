package com.xing.paper.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xing.paper.po.NoticePO;
import com.xing.paper.po.PaperPO;
import com.xing.paper.po.SelectionPo;
import com.xing.paper.po.StudentPo;
import com.xing.paper.po.TeacherPo;
import com.xing.paper.po.TopicPo;
import com.xing.paper.service.AdminService;
import com.xing.paper.service.DepartmentService;
import com.xing.paper.service.StudentService;
import com.xing.paper.service.TeacherService;
import com.xing.paper.service.TopicService;
import com.xing.paper.service.impl.SelectionServiceImpl;
import com.xing.paper.utils.GenerateGradeUtil;
import com.xing.paper.utils.GenerateStatusUtil;
import com.xing.paper.utils.GenerateTeacherTitleUtil;

@Controller
public class TeacherController {

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

	@RequestMapping(value = "/teacher/index")
	public String getIndexPage(HttpServletRequest request,HttpServletResponse response) {
		String teacherNumber = (String) request.getSession().getAttribute("teacherNumber");
		TeacherPo teacher = (teacherService.queryByTeacherNumber(teacherNumber));
		request.setAttribute("topics", topicService.getTopicVoByTeacher(teacher));
		return "teacher/teacher-index";
	}

	@RequestMapping(value = "/teacher/status/{studentNumber}.html")
	public String getProcessingPage(@PathVariable(value = "studentNumber") String studentNumber,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		StudentPo student = studentService.getStudentByNumber(studentNumber);
		String teacherNumber = (String) request.getSession().getAttribute("teacherNumber");
		if(student!=null){
			SelectionPo selection = selectionService.queryByStudent(student);
			if(selection!=null && selection.getTeacher().getTeacherNumber().equals(teacherNumber) && selection.getGrade()==null){
				request.setAttribute("info", selection);
				request.setAttribute("status", GenerateStatusUtil.generateStatus());
			}else{
				response.sendRedirect(request.getContextPath());
			}
		}else{
			response.sendRedirect(request.getContextPath());
		}
		return "teacher/teacher-change-status";
	}

	@RequestMapping(value = "/teacher/grade/{studentNumber}.html")
	public String getGradePage(@PathVariable(value = "studentNumber") String studentNumber, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		StudentPo student = studentService.getStudentByNumber(studentNumber);
		String teacherNumber = (String) request.getSession().getAttribute("teacherNumber");
		if(student!=null){
			SelectionPo selection = selectionService.queryByStudent(student);
			if(selection!=null && selection.getTeacher().getTeacherNumber().equals(teacherNumber) && selection.getGrade()==null){
				request.setAttribute("info", selection);
				request.setAttribute("grade", GenerateGradeUtil.generateGrade());
			}else{
				response.sendRedirect(request.getContextPath());
			}
			List<PaperPO> papers = studentService.getPaperByStudentNumber(student.getStudentNumber(),null);
			if(papers.size()>0){
				request.setAttribute("papers",papers);
			}
		}else{
			response.sendRedirect(request.getContextPath());
		}
		return "teacher/teacher-change-grade";
	}

	@RequestMapping(value = "/teacher/info")
	public String getInfoPage(HttpServletRequest request, HttpServletResponse response) {
		String teacherNumber = (String) request.getSession().getAttribute("teacherNumber");
		request.setAttribute("departments", departmentService.getAllDepartment());
		request.setAttribute("info", teacherService.queryByTeacherNumber(teacherNumber));
		request.setAttribute("titles", GenerateTeacherTitleUtil.generateTeacherTitle());
		return "teacher/teacher-edit-info";
	}
	
	@RequestMapping(value="/teacher/info/modify")
	public void doTeacherModify(@RequestParam(value = "teacherNumber") String teacherNumber,HttpServletRequest request, HttpServletResponse response) throws IOException {
		TeacherPo teacher = teacherService.queryByTeacherNumber(teacherNumber);
		if(teacher!=null){
			teacher.setDepartment(departmentService.getDepartmentById(request.getParameter("dept")));
			teacher.setGender(request.getParameter("gender"));
			if(request.getParameter("password")!=null && request.getParameter("password").length()>0){
				teacher.setPassword(request.getParameter("password"));
			}
			teacher.setPhone(request.getParameter("phone"));
			teacher.setTeacherName(request.getParameter("name"));
			teacher.setTitle(request.getParameter("title"));
			if(!teacher.getTitle().contains("教授")){
				request.getSession().setAttribute("username", teacher.getTeacherName()+" 老师");
			}else{
				request.getSession().setAttribute("username", teacher.getTeacherName()+" "+teacher.getTitle());
			}
			teacherService.upgradeTeacher(teacher);
		}
		response.sendRedirect(request.getContextPath() + "/teacher/info");
	}

	@RequestMapping(value = "/teacher/direction")
	public String getDirectionPage(HttpServletRequest request, HttpServletResponse response) {
		String teacherNumber = (String) request.getSession().getAttribute("teacherNumber");
		TeacherPo teacher = (teacherService.queryByTeacherNumber(teacherNumber));
		request.setAttribute("topics", topicService.getTopicVoByTeacher(teacher));
		return "teacher/teacher-add-direction";
	}
	
	@RequestMapping(value = "/teacher/direction/{id}.html")
	public String getEditPage(@PathVariable(value = "id") String id,HttpServletRequest request, HttpServletResponse response) throws IOException {
		String teacherNumber = (String) request.getSession().getAttribute("teacherNumber");
		TopicPo topic = topicService.queryByTopicId(id);
		if(topic.getTeacher().getTeacherNumber().equals(teacherNumber)){
			request.setAttribute("topics", topic);
		}else{
			response.sendRedirect(request.getContextPath());
		}
		return "teacher/teacher-edit-direction";
	}

	@RequestMapping(value = "/teacher/direction/update")
	public void doTopicUpdate(@RequestParam(required=true)String id,HttpServletRequest request, HttpServletResponse response) throws IOException {
		String teacherNumber = (String) request.getSession().getAttribute("teacherNumber");
		if(id!=null && id.length()>0){
			TopicPo topicPo = topicService.queryByTopicId(id);
			if(topicPo!=null && topicPo.getTeacher().getTeacherNumber().equals(teacherNumber)){
				topicPo.setTopic(request.getParameter("topic"));
				topicService.upgradeTopic(topicPo);
			}
		}
		response.sendRedirect(request.getContextPath()+"/teacher/direction");
	}
	
	@RequestMapping(value = "/teacher/direction/add")
	public void doTopicAdd(@RequestParam(required=true)String topicName,HttpServletRequest request, HttpServletResponse response) throws IOException {
		String teacherNumber = (String) request.getSession().getAttribute("teacherNumber");
		if(topicName!=null && topicName.length()>0){
			TopicPo topic = new TopicPo();
			topic.setTeacher(teacherService.queryByTeacherNumber(teacherNumber));
			topic.setTopic(topicName);
			topicService.addTopic(topic);
		}
		response.sendRedirect(request.getContextPath()+"/teacher/direction");
	}
	
	@RequestMapping(value = "/teacher/direction/delete")
	public void doTopicModify(@RequestParam(required=true)String id,HttpServletRequest request, HttpServletResponse response) throws IOException {
		String teacherNumber = (String) request.getSession().getAttribute("teacherNumber");
		if(id!=null && id.length()>0){
			TopicPo topic = topicService.queryByTopicId(id);
			if(topic!=null && topic.getTeacher().getTeacherNumber().equals(teacherNumber)){
				topicService.deleteTopic(topic);
			}
		}
		response.sendRedirect(request.getContextPath()+"/teacher/direction");
	}
	
	@RequestMapping(produces="application/json;charset=utf-8",value="/teacher/status/update")
	@ResponseBody
	public Map<String,Object> doStatusUpdate(HttpServletRequest request, HttpServletResponse response){
		Map<String,Object> res = new HashMap<String,Object>();
		String studentNumber = request.getParameter("studentNumber");
		String status = request.getParameter("status");
		StudentPo student = studentService.getStudentByNumber(studentNumber);
		if(student!=null){
			SelectionPo selection = selectionService.queryByStudent(student);
			if(selection != null){
				selection.setProcessing(status);
				selectionService.upgradeSelection(selection);
				res.put("status", "success");
			}else{
				res.put("status", "failed");
			}
		}else{
			res.put("status", "failed");
		}
		return res;
	}
	
	@RequestMapping(produces="application/json;charset=utf-8",value="/teacher/grade/update")
	@ResponseBody
	public Map<String,Object> doGradeUpdate(HttpServletRequest request, HttpServletResponse response){
		Map<String,Object> res = new HashMap<String,Object>();
		String studentNumber = request.getParameter("studentNumber");
		String update = request.getParameter("grade");
		StudentPo student = studentService.getStudentByNumber(studentNumber);
		if(student!=null){
			SelectionPo selection = selectionService.queryByStudent(student);
			if(selection != null){
				selection.setGrade(update);
				selection.setProcessing("已经完成成绩登记");
				selectionService.upgradeSelection(selection);
				res.put("status", "success");
			}else{
				res.put("status", "failed");
			}
		}else{
			res.put("status", "failed");
		}
		return res;
	}
	
	
	//论文审批页面
	@RequestMapping(value = "/teacher/approve")
	public String approvePaper(Integer id,HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<PaperPO> papers = studentService.getPaperByStudentNumber(null,id);
		request.setAttribute("paper", papers.get(0));
		return "teacher/teacher-edit-approve";
	}
	
	//论文审批页面
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/teacher/approvePaper")
	@ResponseBody
	public Map approvePapers(PaperPO paperPO,HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		Map<String,Object> res = new HashMap<String,Object>();
		try {
			List<PaperPO> papers = studentService.getPaperByStudentNumber(null,paperPO.getId());
			PaperPO po = papers.get(0);
			StudentPo studentPo = studentService.getStudentByNumber(po.getF_student_number());
			po.setComment(paperPO.getComment());
			po.setPaper_content(paperPO.getPaper_content());
			po.setPaper_status(paperPO.getPaper_status());
			studentService.updatePaper(po,studentPo);	
			res.put("status", "success");
		} catch (Exception e) {
			e.printStackTrace();
			res.put("status", "failed");
		}
		return res;
	}
	
	@RequestMapping(value = "/teacher/notice")
	public String getNoticePage(HttpServletRequest request, HttpServletResponse response) {
		List<NoticePO> notices = adminService.getAllNotice();
		request.setAttribute("notices", notices);
		return "teacher/notice-index";
	}

}
