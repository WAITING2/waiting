<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<title>学位论文提交系统</title>
<link rel="stylesheet" href="${pageContext.request.contextPath }/res/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/res/css/wangEditor.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/res/js/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/res/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/res/js/wangEditor.min.js"></script>
<style>
  body{
    font-family: "Microsoft Yahei";
  }
</style>
</head>
<body>
  <nav class="navbar navbar-default">
  <div class="container-fluid">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#nav-bar" aria-expanded="false">
        <span class="sr-only">导航按钮</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="#">学位论文提交系统</a>
    </div>

    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="nav-bar">
      <ul class="nav navbar-nav">
        <li class="active"><a href="/">管理学生用户</a></li>
        <li><a href="${pageContext.request.contextPath }/admin/teacher">管理指导老师用户</a></li>
        <li><a href="${pageContext.request.contextPath }/admin/administrator">管理系统管理员账户</a></li>
        <li><a href="${pageContext.request.contextPath }/admin/selection">查看选题情况</a></li>
        <li><a href="${pageContext.request.contextPath }/admin/grade">查看已完成学生成绩</a></li>
        <li><a href="${pageContext.request.contextPath }/admin/notice">公告管理</a></li>
        <li><a href="${pageContext.request.contextPath }/admin/getStudentPaperInfo">查询导出</a></li>
      </ul>
      <ul class="nav navbar-nav navbar-right">
          <li class="dropdown">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">您好，${sessionScope.username } <span class="caret"></span></a>
            <ul class="dropdown-menu">
              <li><a href="${pageContext.request.contextPath }/logout">退出</a></li>
            </ul>
          </li>
       </ul>
    </div>
  </div>
</nav>
<div class="container">
  <div class="row">
      <div class="col-md-6 col-xs-6">
        <h4 style="padding-left:8px;">学生管理</h4>
      </div>
      <div class="col-md-6 col-xs-6">
        <a href="${pageContext.request.contextPath }/admin/student/add.html" class="btn btn-primary btn" style="float:right;">添加新学生</a>
      </div>
    </div>
  <table class="table">
    <tr><th>学号</th><th>姓名</th><th>性别</th><th>专业</th><th>班级</th><th>操作</th></tr>
    <c:forEach items="${requestScope.students }" var="item">
    	  <tr><td>${item.studentNumber }</td><td>${item.studentName }</td><td>${item.gender }</td><td>${item.department.departmentName }</td><td>${item.classPO.className }</td><td><a href="${pageContext.request.contextPath }/admin/student/${item.studentNumber }.html" class="btn btn-warning btn-sm">修改</a> <a href="${pageContext.request.contextPath }/admin/student/remove?studentNumber=${item.studentNumber }"class="btn btn-danger btn-sm">删除</a></td></tr>
    </c:forEach>
    </table>
</body>
</html>
