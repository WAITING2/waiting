package com.xing.paper.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xing.paper.po.AdminPo;
import com.xing.paper.po.Instructor;
import com.xing.paper.po.TeacherPo;
import com.xing.paper.service.AdminService;
import com.xing.paper.service.InstructorService;
import com.xing.paper.service.StudentService;
import com.xing.paper.service.TeacherService;

@Controller
public class LoginController {
	
	@Resource
	AdminService adminService;
	@Resource
	StudentService studentService;
	@Resource
	TeacherService teacherService;
	@Resource
	InstructorService instructorService;

	@RequestMapping(value = "/")
	public void getIndexPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getSession().getAttribute("auth") == null) {
			request.getRequestDispatcher("/login/page").forward(request, response);
		} else {
			String auth = (String) request.getSession().getAttribute("auth");
			if (auth.equals("admin")) {
				request.getRequestDispatcher("/admin/index").forward(request, response);
			} else if (auth.equals("teacher")) {
				request.getRequestDispatcher("/teacher/index").forward(request, response);
			} else if(auth.equals("student")) {
				request.getRequestDispatcher("/student/index").forward(request, response);
			}else{
				request.getRequestDispatcher("/instructor/index").forward(request, response);
			}
		}
	}
	
	@RequestMapping(value="login/page")
	public String getLoginPage(HttpServletRequest request, HttpServletResponse response){
		request.setAttribute("hints", request.getSession().getAttribute("hints"));
		request.getSession().removeAttribute("hints");
		return "common/login";
	}
	
	@RequestMapping(value="logout")
	@ResponseBody
	public void doLogout(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		request.getSession().invalidate();
		request.getSession().setAttribute("hints", "<div class=\"alert alert-success\" role=\"alert\">登出成功</div>");
		response.sendRedirect(request.getContextPath());
	}

	@RequestMapping(value = "login")
	@ResponseBody
	public void doLogin(@RequestParam(name = "account") String account,
			@RequestParam(name = "username") String username, @RequestParam(name = "password") String password,
			HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		boolean isLogin = false;
		if(account!=null){
			if(account.equals("admin")){
				isLogin = adminService.comfirmLogin(username, password);
				if(isLogin){
					AdminPo admin = adminService.getAdmin(username);
					request.getSession().setAttribute("auth", "admin");
					request.getSession().setAttribute("username", "管理员 "+admin.getUserName());
					request.getSession().setAttribute("adminNumber", admin.getId());
				}
			}else if(account.equals("teacher")){
				isLogin = teacherService.comfirmLogin(username, password);
				if(isLogin){
					request.getSession().setAttribute("auth", "teacher");
					TeacherPo teacher =  teacherService.queryByTeacherNumber(username);
					if(!teacher.getTitle().contains("教授")){
						request.getSession().setAttribute("username", teacher.getTeacherName()+" 老师");
					}else{
						request.getSession().setAttribute("username", teacher.getTeacherName()+" "+teacher.getTitle());
					}
					request.getSession().setAttribute("teacherNumber", teacher.getTeacherNumber());
				}
			}else if(account.equals("student")){
				isLogin = studentService.comfirmLogin(username, password);
				if(isLogin){
					request.getSession().setAttribute("auth", "student");
					request.getSession().setAttribute("studentNumber", username);
					request.getSession().setAttribute("username", studentService.getStudentByNumber(username).getStudentName()+"同学");
				}
			}else if(account.equals("instructor")){
				isLogin = instructorService.comfirmLogin(username, password);
				if(isLogin){
					request.getSession().setAttribute("auth", "instructor");
					Instructor instructor = instructorService.queryByInstructorNumber(username);
					request.getSession().setAttribute("instructorNumber",instructor.getInstructorNumber());
					request.getSession().setAttribute("username", instructor.getInstructorName()+"辅导员");
				}
			}
		}
		if(!isLogin){
			request.getSession().setAttribute("hints", "<div class=\"alert alert-danger\" role=\"alert\">登录出错了，请检查用户名、密码有没有错误哦</div>");
		}
		response.sendRedirect(request.getContextPath());
	}

}
