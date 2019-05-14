package com.xing.paper.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xing.paper.po.*;
import com.xing.paper.service.*;
import com.xing.paper.vo.PaperVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xing.paper.service.impl.SelectionServiceImpl;
import com.xing.paper.utils.GenerateTeacherTitleUtil;

@Controller
public class AdminController {

	@Resource
	AdminService adminService;
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
	InstructorService instructorService;

	@RequestMapping(value = "/admin/index")
	public String adminIndex(HttpServletRequest request) {
		List<StudentPo> students = studentService.getAllStudent();
		request.setAttribute("students", students);
		return "admin/manager-student";
	}

	@RequestMapping(value = "/admin/student/add.html")
	public String addStudentPage(HttpServletRequest request) {
		List<ClassPO> classes = instructorService.findAllClass();//查询出所有class信息
		request.setAttribute("classes", classes);
		request.setAttribute("departments", departmentService.getAllDepartment());
		request.setAttribute("formAction", request.getContextPath() + "/admin/student/add");
		return "admin/manager-edit-student";
	}

	@RequestMapping(value = "/admin/student/{id}.html")
	public String editStudentPage(@PathVariable(value = "id") String id, HttpServletRequest request) {
		request.setAttribute("student", studentService.getStudentByNumber(id));
		request.setAttribute("departments", departmentService.getAllDepartment());
		List<ClassPO> classes = instructorService.findAllClass();//查询出所有class信息
		request.setAttribute("classes", classes);
		request.setAttribute("formAction", request.getContextPath() + "/admin/student/modify");
		return "admin/manager-edit-student";
	}
	///student/checkStudentNum

	@RequestMapping(value = "/admin/student/checkStudentNum")
	@ResponseBody
	public Object checkStudentNum( String student_number,
	HttpServletRequest request) {
		Map<String,Object> map = new HashMap<String,Object>();

		//验证是否存在此id的

		StudentPo student = studentService.getStudentByNumber(student_number);

		if(null == student){//可以正常添加

			map.put("success",true);

		}else{

			map.put("success",false);

		}
//
//		request.setAttribute("classes", classes);
//		request.setAttribute("formAction", request.getContextPath() + "/admin/student/modify");
		return map;
	}

	@RequestMapping(value = "/admin/teacher/checkTeacherNum")
	@ResponseBody
	public Object checkTeacherNum( String teacher_number,
								   HttpServletRequest request) {
		Map<String,Object> map = new HashMap<String,Object>();

		//验证是否存在此id的

		TeacherPo teacherPo = teacherService.queryByTeacherNumber(teacher_number);

		if(null == teacherPo){//可以正常添加

			map.put("success",true);

		}else{

			map.put("success",false);

		}
//
//		request.setAttribute("classes", classes);
//		request.setAttribute("formAction", request.getContextPath() + "/admin/student/modify");
		return map;
	}


	@RequestMapping(value = "/admin/student/add")
//	@ResponseBody
	public void addStudent(@RequestParam(name = "studentNumber") String studentNumber, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String deptId = request.getParameter("department");
		DepartmentPo dept = null;
		if (deptId != null) {
			dept = departmentService.getDepartmentById(deptId);
		}
		if (StringUtils.isNotBlank(studentNumber)  && dept != null) {
			StudentPo student = new StudentPo();
			student.setClassName(request.getParameter("cls"));
			student.setDepartment(dept);
			student.setGender(request.getParameter("gender"));
			student.setPassword(request.getParameter("password"));
			student.setPhone(request.getParameter("phone"));
			student.setStudentName(request.getParameter("name"));
			student.setStudentNumber(studentNumber);
			student.setEmail(request.getParameter("email"));
			//学生初始密码为111111
			if(StringUtils.isEmpty(request.getParameter("password")))
			{
				student.setPassword("111111");
			}
			studentService.addStudent(student);
		}
		response.sendRedirect(request.getContextPath()+"/");
	}

	@RequestMapping(value = "/admin/student/modify")
//	@ResponseBody
	public void editStudent(@RequestParam(name = "studentNumber") String studentNumber, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
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
			student.setClassName(request.getParameter("cls"));
			student.setDepartment(dept);
			student.setGender(request.getParameter("gender"));
			student.setPassword(request.getParameter("password"));
			student.setPhone(request.getParameter("phone"));
			student.setStudentName(request.getParameter("name"));
			student.setEmail(request.getParameter("email"));
			studentService.updateStudent(student);
		}
		response.sendRedirect(request.getContextPath()+"/");
	}

	@RequestMapping(value = "/admin/student/remove")
//	@ResponseBody
	public void deleteStudent(@RequestParam(name = "studentNumber") String studentNumber, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		if (studentNumber != null) {
			StudentPo student = studentService.getStudentByNumber(studentNumber);
			if (student != null) {
				studentService.deleteStudent(student);
			}
		}
		response.sendRedirect(request.getContextPath()+"/");
	}

	@RequestMapping(value = "/admin/teacher")
	public String getTeacherManagerPage(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("teachers", teacherService.getAllTeacher());
		return "admin/manager-teacher";
	}

	@RequestMapping(value = "/admin/teacher/add.html")
	public String getTeacherAddPage(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("departments", departmentService.getAllDepartment());
		request.setAttribute("titles", GenerateTeacherTitleUtil.generateTeacherTitle());
		request.setAttribute("formAction", request.getContextPath() + "/admin/teacher/add");
		return "admin/manager-edit-teacher";
	}

	@RequestMapping(value = "/admin/teacher/add")
	@ResponseBody
	public void doTeacherAdd(@RequestParam(value = "teacherNumber") String teacherNumber, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		TeacherPo teacher = new TeacherPo();
		teacher.setDepartment(departmentService.getDepartmentById(request.getParameter("dept")));
		teacher.setGender(request.getParameter("gender"));
		teacher.setPassword(request.getParameter("password"));
		teacher.setPhone(request.getParameter("phone"));
		teacher.setTeacherName(request.getParameter("name"));
		teacher.setTeacherNumber(teacherNumber);
		teacher.setTitle(request.getParameter("title"));
		//教师初始密码为666666
		if(StringUtils.isEmpty(request.getParameter("password")))
		{
			teacher.setPassword("666666");
		}
		teacherService.addTeacher(teacher);
		response.sendRedirect(request.getContextPath() + "/admin/teacher");
	}

	@RequestMapping(value = "/admin/teacher/modify")
	@ResponseBody
	public void doTeacherModify(@RequestParam(value = "teacherNumber") String teacherNumber,HttpServletRequest request, HttpServletResponse response) throws IOException {
		TeacherPo teacher = teacherService.queryByTeacherNumber(teacherNumber);
		if(teacher!=null){
			teacher.setDepartment(departmentService.getDepartmentById(request.getParameter("dept")));
			teacher.setGender(request.getParameter("gender"));
			teacher.setPassword(request.getParameter("password"));
			teacher.setPhone(request.getParameter("phone"));
			teacher.setTeacherName(request.getParameter("name"));
			teacher.setTitle(request.getParameter("title"));
			teacherService.upgradeTeacher(teacher);
		}
		response.sendRedirect(request.getContextPath() + "/admin/teacher");
	}

	@RequestMapping(value = "/admin/teacher/remove")
//	@ResponseBody
	public void doTeacherRemove(@RequestParam(value = "teacherNumber") String teacherNumber, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		TeacherPo teacher = teacherService.queryByTeacherNumber(teacherNumber);
		if (teacher != null) {
			teacherService.deleteTeacher(teacher);
		}
		response.sendRedirect(request.getContextPath() + "/admin/teacher");
	}

	@RequestMapping(value = "/admin/teacher/{id}.html")
	public String getTeacherEditPage(@PathVariable() String id, HttpServletRequest request,
			HttpServletResponse response) {
		request.setAttribute("departments", departmentService.getAllDepartment());
		request.setAttribute("info", teacherService.queryByTeacherNumber(id));
		request.setAttribute("titles", GenerateTeacherTitleUtil.generateTeacherTitle());
		request.setAttribute("formAction", request.getContextPath() + "/admin/teacher/modify");
		return "admin/manager-edit-teacher";
	}

	@RequestMapping(value = "/admin/administrator")
	public String getAdminManagerPage(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("admins", adminService.getAllAdmin());
		return "admin/manager-admin";
	}

	@RequestMapping(value = "/admin/administrator/add.html")
	public String getAdminAddPage(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("formAction", request.getContextPath() + "/admin/administrator/add");
		return "admin/manager-edit-admin";
	}

	@RequestMapping(value = "/admin/administrator/add")
	@ResponseBody
	public void doAdminAdd(@RequestParam(value = "username") String username,HttpServletRequest request, HttpServletResponse response) throws IOException {
		AdminPo admin =  new AdminPo();
		admin.setUserName(username);
		admin.setPassword(request.getParameter("password"));

		//管理员初始密码为999999
		if(StringUtils.isEmpty(request.getParameter("password")))
		{
			admin.setPassword("999999");
		}
		adminService.addAdmin(admin);
		response.sendRedirect(request.getContextPath() + "/admin/administrator");
	}

	@RequestMapping(value = "/admin/administrator/modify")
	@ResponseBody
	public void doAdminModify(@RequestParam(value = "username") String username, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		AdminPo admin = adminService.getAdmin(username);
		if(admin!=null){
			admin.setPassword(request.getParameter("password"));
			adminService.upgradeAdmin(admin);
		}
		response.sendRedirect(request.getContextPath() + "/admin/administrator");
	}

	@RequestMapping(value = "/admin/administrator/remove")
	public void doAdminRemove(@RequestParam(value = "id") String id, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		AdminPo admin = adminService.getAdminById(id);
		if(admin!=null){
			adminService.deleteAdmin(admin);
		}
		response.sendRedirect(request.getContextPath() + "/admin/administrator");
	}

	@RequestMapping(value = "/admin/administrator/{id}.html")
	public String getAdminiEditPage(@PathVariable(value = "id") String id, HttpServletRequest request,
			HttpServletResponse response) {
		request.setAttribute("info", adminService.getAdminById(id));
		request.setAttribute("formAction", request.getContextPath() + "/admin/administrator/modify");
		return "admin/manager-edit-admin";
	}

	@RequestMapping(value = "/admin/selection")
	public String getSelectionManagerPage(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("selections", selectionService.getAllSelection());
		return "admin/manager-select";
	}
	
	@RequestMapping("/admin/selection/remove")
	public void doSelectionRemove(HttpServletRequest request, HttpServletResponse response) throws IOException {
		StudentPo student = studentService.getStudentByNumber(request.getParameter("student"));
		if(student!=null){
			SelectionPo selection = selectionService.queryByStudent(student);
			if(selection !=null){
				selectionService.deleteSelection(selection);
			}
		}
		response.sendRedirect(request.getContextPath()+"/admin/selection");
	}

	@RequestMapping(value = "/admin/grade")
	public String getGradeManagerPage(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("selections", selectionService.getAllFinishSelection());
		return "admin/manager-grade";
	}
	
	@RequestMapping(value = "/admin/notice")
	public String getNoticePage(HttpServletRequest request, HttpServletResponse response) {
		List<NoticePO> notices = adminService.getAllNotice();
		request.setAttribute("notices", notices);
		request.setAttribute("admin", true);
		return "admin/notice-index";
	}
	
	@RequestMapping(value = "/admin/notice/add.html")
	public String getNoticeAddPage(HttpServletRequest request, HttpServletResponse response) {
		return "admin/manager-edit-notice";
	}
	
	@RequestMapping(value = "/admin/editNotice")
	public String editNotice(HttpServletRequest request, HttpServletResponse response,Integer id) {
		NoticePO notice = adminService.getNoticeById(id);
		request.setAttribute("notice", notice);
		return "admin/manager-edit-notice";
	}
	
	@RequestMapping(value = "/admin/notice/operate")
	public void addNotice(HttpServletRequest request, HttpServletResponse response,NoticePO notice) throws IOException {
		if(notice.getId() != null){//修改
			adminService.updateNotice(notice);
		}else{//新增
			String adminId = (String)request.getSession().getAttribute("adminNumber");
			notice.setCreatedate(new Date());
			notice.setAdmin_id(adminId);
			adminService.addNotice(notice);
		}
		response.sendRedirect(request.getContextPath()+"/admin/notice");
	}
	
	@RequestMapping(value = "/admin/delNotice")
	public void delNotice(HttpServletRequest request, HttpServletResponse response,Integer id) throws IOException {
		adminService.delNoticeById(id);
		response.sendRedirect(request.getContextPath()+"/admin/notice");
	}


	@RequestMapping(value="/admin/getStudentPaperInfo")
	public String getStudentPaperInfo(HttpServletRequest request,HttpServletResponse reponse,
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
		return "admin/manager-export";
	}

}
