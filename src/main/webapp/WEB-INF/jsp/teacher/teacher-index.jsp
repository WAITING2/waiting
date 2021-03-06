<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<title>学位论文提交系统</title>
<link rel="stylesheet" href="res/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="res/css/wangEditor.css">
<script type="text/javascript" src="res/js/jquery.min.js"></script>
<script type="text/javascript" src="res/js/bootstrap.min.js"></script>
<script type="text/javascript" src="res/js/wangEditor.min.js"></script>
<style>
  body{
    font-family: "Microsoft Yahei";
  }
</style>
</head>
<body>
  <nav class="navbar navbar-inverse" style="margin-bottom:0px;border-radius:0px;">
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
        <li class="active"><a href="/">首页</a></li>
        <li><a href="${pageContext.request.contextPath }/teacher/direction">选题申报</a></li>
        <li><a href="${pageContext.request.contextPath }/teacher/info">信息管理</a></li>
        <li ><a href="${pageContext.request.contextPath }/teacher/notice">公告</a></li>
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
<div class="jumbotron">
  <div class="container">
    <h3>欢迎您，${sessionScope.username } </h3>
    <p>欢迎使用学位论文提交系统</p>
  </div>
</div>
<div class="container">
  <section>
    <h4>选题情况跟踪：</h4>
    <table class="table">
      <tr><th>选题</th><th>学生</th><th>学生所属学院</th><th>状态</th><th>操作<th></tr>
      <c:forEach items="${requestScope.topics }" var="item">
      <c:if test="${item.isSelect() == true && item.grade == null}">
      	    <tr><td>${item.topic }</td><td>${item.student.studentName }</td><td>${item.student.department.departmentName }</td><td>${item.status }</td><td><a href="${pageContext.request.contextPath }/teacher/status/${item.student.studentNumber }.html" class="btn btn-warning btn-sm">修改进度</a>  <a href="${pageContext.request.contextPath }/teacher/grade/${item.student.studentNumber }.html" class="btn btn-success btn-sm">成绩评定</a></td></tr>
      </c:if>
      <c:if test="${item.isSelect() == false || item.grade != null}">
       	    <tr><td>${item.topic }</td><td>${item.student.studentName }</td><td>${item.student.department.departmentName }</td><td>${item.status }</td><td>不可操作</td></tr>     
      </c:if>
      </c:forEach>
     </table>
  </section>
</div>
</body>
</html>
