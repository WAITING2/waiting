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
        <li><a href="/">首页</a></li>
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
    <h3>欢迎您，${sessionScope.username }老师</h3>
    <p>欢迎使用学位论文提交系统</p>
  </div>
</div>
<div class="container">
  <section>
    <h4>选题进度修改</h4>
    <table class="table">
      <tr><td class="col-md-6">选题名称</td><td class="col-md-6">${requestScope.info.topic.topic }</td></tr>
      <tr><td>学生姓名</td><td>${requestScope.info.student.studentName }</td></tr>
      <tr><td>学生编号</td><td>${requestScope.info.student.studentNumber }</td></tr>
      <tr><td>联系电话</td><td>${requestScope.info.student.phone }</td></tr>
      <tr><td>专业</td><td>${requestScope.info.student.department.departmentName }</td></tr>
      <tr><td>完成进度</td><td>
        <select class="form-control" id="status">
          <c:forEach items="${requestScope.status }" var="item">
          		<c:choose>
          			<c:when test="${item.equals(requestScope.info.processing) }">
          				<option value="${item }" selected="true">${item }</option>
          			</c:when>
          			<c:otherwise>
          				<option value="${item }">${item }</option>
          			</c:otherwise>
          		</c:choose>
          </c:forEach>
        </select>
      </td></tr>
    </table>
    <button type="button" class="btn btn-success" id="modify">修改</button>
  </section>
  <script type="text/javascript">
  		$("#modify").click(function(){
  			$.post("${pageContext.request.contextPath }/teacher/status/update",{
  				"studentNumber":"${requestScope.info.student.studentNumber}",
  				"status":$("#status").val()
  			},function(result){
  			   if(result.status=='success'){
  				   alert("更新成功!");
  				   window.location.href="${pageContext.request.contextPath }"+"/";
  			   }else{
  				   alert("更新失败");
  			   }
  			});
  		});
  </script>
</div>
</body>
</html>
 