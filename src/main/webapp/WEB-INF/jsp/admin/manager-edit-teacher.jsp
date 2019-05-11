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
        <li><a href="${pageContext.request.contextPath }">管理学生用户</a></li>
        <li class="active"><a href="/">管理指导老师用户</a></li>
        <li><a href="${pageContext.request.contextPath }/admin/administrator">管理系统管理员账户</a></li>
        <li><a href="${pageContext.request.contextPath }/admin/selection">查看选题情况</a></li>
        <li><a href="${pageContext.request.contextPath }/admin/grade">查看已完成学生成绩</a></li>
        <li><a href="${pageContext.request.contextPath }/admin/notice">公告管理</a></li>
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
  <form action="${requestScope.formAction }" method="POST">
  <div class="form-group">
    <label for="id">教师工号</label>
    <c:choose>
    	<c:when test="${requestScope.formAction.contains(\"modify\")}">
    		<input type="text" class="form-control" id="teacherNumber" name="teacherNumber" value="${requestScope.info.teacherNumber }" disabled>
    		<input type="text" class="form-control" id="teacherNumber" name="teacherNumber" value="${requestScope.info.teacherNumber }" hidden="true" style="display:none;">
    	</c:when>
    	<c:otherwise>
			<input type="text" class="form-control" id="teacherNumber" name="teacherNumber" placeholder="请输入工号" value="">   		 </c:otherwise>
    	</c:choose>
  </div>
  <div class="form-group">
    <label for="name">教师姓名</label>
    <input type="text" class="form-control" id="name" name="name" placeholder="请输入姓名" value="${requestScope.info.teacherName }">
  </div>
  <div class="form-group">
    <label for="phone">联系电话</label>
    <input type="text" class="form-control" id="phone" name="phone" placeholder="请输入联系电话" value="${requestScope.info.phone }">
  </div>
  <div class="form-group">
    <label for="class">教师职称</label>
    <select class="form-control" id="title" name="title">
    <c:forEach items="${requestScope.titles }" var="title">
    		<c:if test="${requestScope.info.title == title }">
    			<option value="${title }" selected="true">${title }</option>
    		</c:if>
    		<c:if test="${requestScope.info.title != title }">
    			<option value="${title }">${title }</option>
    		</c:if>
    </c:forEach>
    </select>
  </div>
  <div class="form-group">
    <label for="title">教师性别</label>
    <c:choose>
    	<c:when test="${requestScope.info.gender == \"男\" }">
    		<select class="form-control" id="gender" name="gender"><option value="男" selected="true">男</option><option value="女">女</option></select>
    	</c:when>
    	<c:when test="${requestScope.info.gender == \"女\" }">
    		<select class="form-control" id="gender" name="gender"><option value="男">男</option><option value="女" selected="true">女</option></select>
    	</c:when>
    	<c:otherwise>
    		<select class="form-control" id="gender" name="gender"><option value="男">男</option><option value="女">女</option></select>
    	</c:otherwise>
    </c:choose>
  </div>
  <div class="form-group">
    <label for="dept">所属专业</label>
    <select class="form-control" id="dept" name="dept">
    	<option value="">请选择</option>
    	<c:forEach items="${requestScope.departments }" var="dept">
    		<c:if test="${requestScope.info.department.departmentName == dept.departmentName }">
    			<option value="${dept.departmentId }" selected="true">${dept.departmentName }</option>
    		</c:if>
    		<c:if test="${requestScope.student.department.departmentName != dept.departmentName }">
    			<option value="${dept.departmentId }">${dept.departmentName }</option>
    		</c:if>
    	</c:forEach>
	</select>
  </div>
  <div class="form-group">
    <label for="password">登录密码</label>
    <input type="password" class="form-control" id="password"  name="password" placeholder="请输入密码">
  </div>
  <button type="submit" class="btn btn-success">提交</button>
</form>
</div>
</body>
</html>
