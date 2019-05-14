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
  /*#title_div{*/

      /*background-size: 100%;*/

      /*background: url(image/xsz.jpg),*/

      /*no-repeat 10px center top*/



  /*}*/
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
      <a class="navbar-brand" href="${pageContext.request.contextPath }">学位论文提交系统</a>
    </div>

    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="nav-bar">
      <ul class="nav navbar-nav">
        <li class="active"><a href="/">首页</a></li>
        <li><a href="${pageContext.request.contextPath }/student/select">选题管理</a></li>
        <li><a href="${pageContext.request.contextPath }/student/info">信息管理</a></li>
          <li><a href="${pageContext.request.contextPath }/student/paper">论文管理</a></li>
           <li><a href="${pageContext.request.contextPath }/student/notice">公告</a></li>
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
<div class="jumbotron" id="title_div" >
    <%--<img src="/res/image/xsz.jpg" alt="">--%>
  <div class="container">
    <h3>欢迎您，${sessionScope.username }</h3>
    <p>欢迎使用学位论文提交系统</p>
  </div>
</div>
<div class="container">
  <section>
    <h4>选题情况</h4>
    <c:choose>
    	<c:when test="${requestScope.selectionInfo == null }">
    		<p>Oops...未进行选题</p>
    		<a href="${pageContext.request.contextPath }/student/select">点这里选题喔~</a>
    	</c:when>
    	<c:otherwise>
    		<table class="table">
     			 <tr><td>学生姓名</td><td>${requestScope.selectionInfo.student.studentName }</td><tr>
     			 <tr><td>选择题目</td><td>${requestScope.selectionInfo.topic.topic}</td><tr>
     			 <tr><td>完成进度</td><td>${requestScope.selectionInfo.processing }</td><tr>
    		</table>
    		<hr>
    		<h4>指导老师信息</h4>
    		<table class="table">
    		  <tr><td>姓名</td><td>${requestScope.selectionInfo.teacher.teacherName }</td><tr>
    		  <tr><td>所属专业</td><td>${requestScope.selectionInfo.teacher.department.departmentName }</td><tr>
    		  <tr><td>性别</td><td>${requestScope.selectionInfo.teacher.gender }</td><tr>
    		  <tr><td>职称</td><td>${requestScope.selectionInfo.teacher.title }</td><tr>
    		  <tr><td>联系电话</td><td>${requestScope.selectionInfo.teacher.phone }</td><tr>
  		  </table>
    	</c:otherwise>
    </c:choose>
    
  </section>
</div>
</body>
</html>
